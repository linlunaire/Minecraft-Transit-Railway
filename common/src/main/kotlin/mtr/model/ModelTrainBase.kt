@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package mtr.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import mtr.MTRClient
import mtr.client.DoorAnimationType
import mtr.client.ScrollingText
import mtr.data.*
import mtr.mappings.ModelMapper
import mtr.render.MoreRenderLayers
import mtr.render.RenderTrains
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import mtr.mappings.EntityModelMapper
import mtr.mappings.RenderBufferSource
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.Entity
import java.util.ArrayList
import java.util.List
import java.util.Locale

@JvmSuppressWildcards
abstract class ModelTrainBase(doorAnimationType: DoorAnimationType?, renderDoorOverlay: Boolean) :
    EntityModelMapper<Entity?>(), IGui {
    @JvmField
    val doorAnimationType: DoorAnimationType?

    @JvmField
    val renderDoorOverlay: Boolean

    private val tempScrollingTexts: MutableList<ScrollingText?> = ArrayList()

    init {
        this.doorAnimationType = doorAnimationType
        this.renderDoorOverlay = renderDoorOverlay
    }

    override fun setupAnim(
        entity: Entity?,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
    }

    final override fun renderToBuffer(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        light: Int,
        overlay: Int,
        color: Int
    ) {
    }

    fun render(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        data: NameColorDataBase?,
        texture: Identifier?,
        light: Int,
        doorLeftValue: Float,
        doorRightValue: Float,
        opening: Boolean,
        currentCar: Int,
        trainCars: Int,
        head1IsFront: Boolean,
        lightsOn: Boolean,
        isTranslucent: Boolean,
        renderDetails: Boolean,
        atPlatform: Boolean
    ) {
        val doorLeftX: Float = DoorAnimationType.getDoorAnimationX(doorAnimationType, doorLeftValue)
        val doorRightX: Float = DoorAnimationType.getDoorAnimationX(doorAnimationType, doorRightValue)
        val doorLeftZ: Float = DoorAnimationType.getDoorAnimationZ(
            doorAnimationType,
            getDoorMax(),
            getDoorDuration(), doorLeftValue, opening
        )
        val doorRightZ: Float = DoorAnimationType.getDoorAnimationZ(
            doorAnimationType,
            getDoorMax(),
            getDoorDuration(), doorRightValue, opening
        )

        val lightOnInteriorLevel = if (lightsOn) IGui.MAX_LIGHT_INTERIOR else light
        val lightOnGlowingLevel = if (lightsOn) IGui.MAX_LIGHT_GLOWING else light

        matrices!!.pushPose()
        baseTransform(matrices)

        if (isTranslucent) {
            if (renderDetails) {
                val renderLayerInteriorTranslucent: RenderType? =
                    if (lightsOn) MoreRenderLayers.getInteriorTranslucent(texture) else MoreRenderLayers.getExteriorTranslucent(
                        texture
                    )
                render(
                    matrices,
                    vertexConsumers!!.getBuffer(renderLayerInteriorTranslucent),
                    RenderStage.INTERIOR_TRANSLUCENT,
                    lightOnInteriorLevel,
                    doorLeftX,
                    doorRightX,
                    doorLeftZ,
                    doorRightZ,
                    currentCar,
                    trainCars,
                    head1IsFront,
                    true
                )
            }
        } else {
            val renderLayerLight: RenderType? =
                if (lightsOn) MoreRenderLayers.getLight(texture, false) else MoreRenderLayers.getExterior(texture)
            val renderLayerInterior: RenderType? =
                if (lightsOn) MoreRenderLayers.getInterior(texture) else MoreRenderLayers.getExterior(texture)
            render(
                matrices,
                vertexConsumers!!.getBuffer(renderLayerLight),
                RenderStage.LIGHTS,
                lightOnGlowingLevel,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                currentCar,
                trainCars,
                head1IsFront,
                renderDetails
            )
            render(
                matrices,
                vertexConsumers!!.getBuffer(renderLayerInterior),
                RenderStage.INTERIOR,
                lightOnInteriorLevel,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                currentCar,
                trainCars,
                head1IsFront,
                renderDetails
            )

            if (renderDetails) {
                renderExtraDetails(
                    matrices,
                    vertexConsumers,
                    light,
                    lightOnInteriorLevel,
                    lightsOn,
                    doorLeftX,
                    doorRightX,
                    doorLeftZ,
                    doorRightZ
                )
            }

            render(
                matrices,
                vertexConsumers!!.getBuffer(MoreRenderLayers.getExterior(texture)),
                RenderStage.EXTERIOR,
                light,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                currentCar,
                trainCars,
                head1IsFront,
                renderDetails
            )
            render(
                matrices,
                vertexConsumers!!.getBuffer(MoreRenderLayers.getLight(texture, true)),
                RenderStage.ALWAYS_ON_LIGHTS,
                IGui.MAX_LIGHT_GLOWING,
                doorLeftX,
                doorRightX,
                doorLeftZ,
                doorRightZ,
                currentCar,
                trainCars,
                head1IsFront,
                renderDetails
            )

            if (renderDetails) {
                val train: TrainClient? = if (data is TrainClient) data else null
                val immediate: RenderBufferSource = RenderTrains.getImmediateBufferSource()
                try {
                    val thisRoute: Route? = if (train == null) null else train.getThisRoute()
                    val nextRoute: Route? = if (train == null) null else train.getNextRoute()
                    val thisStation: Station? = if (train == null) null else train.getThisStation()
                    val nextStation: Station? = if (train == null) null else train.getNextStation()
                    val lastStation: Station? = if (train == null) null else train.getLastStation()
                    renderTextDisplays(
                        matrices,
                        vertexConsumers,
                        Minecraft.getInstance().font,
                        immediate,
                        thisRoute,
                        nextRoute,
                        thisStation,
                        nextStation,
                        lastStation,
                        if (thisRoute == null) null else thisRoute.getDestination(train!!.getCurrentStationIndex()),
                        currentCar,
                        trainCars,
                        atPlatform,
                        if (train == null) tempScrollingTexts else train.scrollingTexts
                    )
                } finally {
                    immediate.endBatch()
                }
            }
        }

        matrices.popPose()
    }

    protected open fun renderExtraDetails(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        light: Int,
        lightOnInteriorLevel: Int,
        lightsOn: Boolean,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float
    ) {
    }

    protected open fun renderTextDisplays(
        matrices: PoseStack?,
        vertexConsumers: RenderBufferSource?,
        font: Font?,
        immediate: RenderBufferSource?,
        thisRoute: Route?,
        nextRoute: Route?,
        thisStation: Station?,
        nextStation: Station?,
        lastStation: Station?,
        customDestination: String?,
        car: Int,
        totalCars: Int,
        atPlatform: Boolean,
        scrollingTexts: MutableList<ScrollingText?>?
    ) {
    }

    protected open fun getDoorDuration(): Float = 0.5f

    protected open fun baseTransform(matrices: PoseStack?) {
    }

    protected abstract fun render(
        matrices: PoseStack?,
        vertices: VertexConsumer?,
        renderStage: RenderStage?,
        light: Int,
        doorLeftX: Float,
        doorRightX: Float,
        doorLeftZ: Float,
        doorRightZ: Float,
        currentCar: Int,
        trainCars: Int,
        head1IsFront: Boolean,
        renderDetails: Boolean
    )

    protected abstract fun getDoorMax(): Int

    protected open fun getDestinationString(
        station: Station?,
        customDestination: String?,
        textSpacingType: TextSpacingType?,
        toUpperCase: Boolean
    ): String? {
        val text: String? =
            if (customDestination == null) if (station == null) defaultDestinationString() else station.name else customDestination
        var finalResult: String?

        if (textSpacingType == TextSpacingType.NORMAL) {
            finalResult = text
        } else {
            val textSplit: Array<String> = (text as java.lang.String).split("\\|")
            val result: MutableList<String?> = ArrayList()
            var hasCjk = false

            for (textPart in textSplit) {
                val isCjk: Boolean = IGui.isCjk(textPart)
                if (textSpacingType == TextSpacingType.SPACE_CJK || textSpacingType == TextSpacingType.SPACE_CJK_FLIPPED) {
                    result.add(
                        if (textSpacingType == TextSpacingType.SPACE_CJK) result.size else 0,
                        if (isCjk && textPart.length == 2) textPart[0].toString() + " " + textPart[1] else textPart
                    )
                } else if (textSpacingType == TextSpacingType.SPACE_CJK_LARGE) {
                    if (isCjk) {
                        val cjkResult: StringBuilder = StringBuilder()
                        for (i in 0..<textPart.length) {
                            cjkResult.append(textPart[i])
                            for (j in 0..<(if (textPart.length == 2) 3 else 1)) {
                                cjkResult.append("   ")
                            }
                        }
                        result.add(cjkResult.toString().trim { it <= ' ' })
                    } else {
                        result.add(textPart)
                    }
                } else if (textSpacingType == TextSpacingType.MLR_SPACING) {
                    var stringBuilder: StringBuilder
                    if (isCjk) {
                        stringBuilder = StringBuilder(textPart)
                        for (i in textPart.length..2) {
                            stringBuilder.append(" ")
                        }
                        hasCjk = true
                    } else {
                        stringBuilder = StringBuilder()
                        for (i in textPart.length..8) {
                            stringBuilder.append(" ")
                        }
                        stringBuilder.append(textPart)
                    }
                    result.add(stringBuilder.toString())
                }
            }

            if (!hasCjk && textSpacingType == TextSpacingType.MLR_SPACING) {
                result.add(0, " ")
                result.add(0, " ")
            }

            finalResult = java.lang.String.join("|", result)
        }

        return if (toUpperCase) finalResult!!.uppercase(Locale.ENGLISH) else finalResult
    }

    protected open fun defaultDestinationString(): String? {
        return ""
    }

    enum class RenderStage {
        LIGHTS, ALWAYS_ON_LIGHTS, INTERIOR, INTERIOR_TRANSLUCENT, EXTERIOR
    }

    protected enum class TextSpacingType {
        NORMAL, SPACE_CJK, SPACE_CJK_FLIPPED, SPACE_CJK_LARGE, MLR_SPACING
    }

    // Retain Java's protected non-final static bridges for existing Java subclasses.
    @Suppress("NON_FINAL_MEMBER_IN_OBJECT")
    companion object {
        // Kotlin has no Java package-protected access; these module-only bridges keep the old static ABI.
        @JvmSynthetic
        internal fun rotatePart(bone: ModelMapper?, x: Float, y: Float, z: Float) = setRotationAngle(bone, x, y, z)

        @JvmSynthetic
        internal fun renderPartMirror(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) = renderMirror(bone, matrices, vertices, light, position)

        @JvmSynthetic
        internal fun renderPartOnce(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) = renderOnce(bone, matrices, vertices, light, position)

        @JvmSynthetic
        internal fun renderPartOnce(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionZ: Float
        ) = renderOnce(bone, matrices, vertices, light, positionX, positionZ)

        @JvmSynthetic
        internal fun renderPartOnceFlipped(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) = renderOnceFlipped(bone, matrices, vertices, light, position)

        @JvmSynthetic
        internal fun renderPartOnceFlipped(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionZ: Float
        ) = renderOnceFlipped(bone, matrices, vertices, light, positionX, positionZ)

        @JvmStatic
        protected open fun setRotationAngle(bone: ModelMapper?, x: Float, y: Float, z: Float) {
            bone!!.setRotationAngle(x, y, z)
        }

        @JvmStatic
        protected open fun renderMirror(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) {
            renderOnce(bone, matrices, vertices, light, position)
            renderOnceFlipped(bone, matrices, vertices, light, position)
        }

        @JvmStatic
        protected open fun renderOnce(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) {
            bone!!.render(matrices, vertices, 0f, position, 0f, light, OverlayTexture.NO_OVERLAY)
        }

        @JvmStatic
        protected open fun renderOnce(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionZ: Float
        ) {
            bone!!.render(matrices, vertices, positionX, positionZ, 0f, light, OverlayTexture.NO_OVERLAY)
        }

        @JvmStatic
        protected open fun renderOnce(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionY: Float,
            positionZ: Float
        ) {
            bone!!.render(matrices, vertices, positionX, positionY, positionZ, 0f, light, OverlayTexture.NO_OVERLAY)
        }

        @JvmStatic
        protected open fun renderOnceFlipped(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            position: Float
        ) {
            bone!!.render(matrices, vertices, 0f, position, Math.PI.toFloat(), light, OverlayTexture.NO_OVERLAY)
        }

        @JvmStatic
        protected open fun renderOnceFlipped(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionZ: Float
        ) {
            bone!!.render(
                matrices,
                vertices,
                -positionX,
                positionZ,
                Math.PI.toFloat(),
                light,
                OverlayTexture.NO_OVERLAY
            )
        }

        @JvmStatic
        protected open fun renderOnceFlipped(
            bone: ModelMapper?,
            matrices: PoseStack?,
            vertices: VertexConsumer?,
            light: Int,
            positionX: Float,
            positionY: Float,
            positionZ: Float
        ) {
            bone!!.render(
                matrices,
                vertices,
                -positionX,
                positionY,
                positionZ,
                Math.PI.toFloat(),
                light,
                OverlayTexture.NO_OVERLAY
            )
        }

        @JvmStatic
        protected open fun isIndex(index: Int, value: Int, array: IntArray?): Boolean {
            val finalIndex = if (index < 0) array!!.size + index else index
            return finalIndex < array!!.size && finalIndex >= 0 && array[finalIndex] == value
        }

        @JvmStatic
        protected open fun getAlternatingString(text: String?): String? {
            val textSplit: Array<String?> = (text as java.lang.String).split("\\|")
            return textSplit[Math.floor((MTRClient.getGameTick() / 30).toDouble()).toInt() % textSplit.size]
        }

        @JvmStatic
        protected open fun getHongKongNextStationString(
            thisStation: Station?,
            nextStation: Station?,
            atPlatform: Boolean,
            isKcr: Boolean
        ): String? {
            if (atPlatform && thisStation != null) {
                return thisStation.name
            } else if (!atPlatform && nextStation != null) {
                return IGui.insertTranslation(
                    if (isKcr) "gui.mtr.next_station_cjk" else "gui.mtr.next_station_announcement_cjk",
                    if (isKcr) "gui.mtr.next_station" else "gui.mtr.next_station_announcement",
                    1,
                    IGui.textOrUntitled(nextStation.name)
                )
            } else {
                return ""
            }
        }

        @JvmStatic
        protected open fun getLondonNextStationString(
            thisRoute: Route?,
            nextRoute: Route?,
            thisStation: Station?,
            nextStation: Station?,
            lastStation: Station?,
            destinationString: String?,
            atPlatform: Boolean
        ): String? {
            val station: Station? = if (atPlatform) thisStation else nextStation
            if (station == null || thisRoute == null) {
                return ""
            } else {
                val messages: MutableList<String?> = ArrayList()
                val isTerminating = lastStation != null && station.id == lastStation.id && nextRoute == null

                if (!isTerminating) {
                    messages.add(
                        IGui.insertTranslation(
                            "gui.mtr.london_train_route_announcement_cjk",
                            "gui.mtr.london_train_route_announcement",
                            2,
                            IGui.textOrUntitled(thisRoute.name),
                            IGui.textOrUntitled(destinationString)
                        )
                    )
                }

                if (atPlatform) {
                    messages.add(
                        IGui.insertTranslation(
                            "gui.mtr.london_train_this_station_announcement_cjk",
                            "gui.mtr.london_train_this_station_announcement",
                            1,
                            IGui.textOrUntitled(station.name)
                        )
                    )
                } else {
                    messages.add(
                        IGui.insertTranslation(
                            "gui.mtr.london_train_next_station_announcement_cjk",
                            "gui.mtr.london_train_next_station_announcement",
                            1,
                            IGui.textOrUntitled(station.name)
                        )
                    )
                }

                val mergedInterchangeRoutes: String =
                    RenderTrains.getInterchangeRouteNames(station, thisRoute, nextRoute)
                if (!mergedInterchangeRoutes.isEmpty()) {
                    messages.add(
                        IGui.insertTranslation(
                            "gui.mtr.london_train_interchange_announcement_cjk",
                            "gui.mtr.london_train_interchange_announcement",
                            1,
                            mergedInterchangeRoutes
                        )
                    )
                }

                if (isTerminating) {
                    messages.add(
                        IGui.insertTranslation(
                            "gui.mtr.london_train_terminating_announcement_cjk",
                            "gui.mtr.london_train_terminating_announcement",
                            1,
                            IGui.textOrUntitled(station.name)
                        )
                    )
                }

                return IGui.formatStationName(IGui.mergeStations(messages, "", " "))
            }
        }
    }
}
