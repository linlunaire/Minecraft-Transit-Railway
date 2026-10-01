import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.VersionRange;

/** Uses the actual Fabric/Maven dependency comparators, including the version-reset negative control. */
public final class RebrandVersionCompatibilityCheck {
    public static void main(String[] args) throws Exception {
        check("1.21.1-3.3.9+yanlingmtr.1.0.0", "1.21.1-3.3.4", "1.21.2", true);
        check("1.21.1-1.0.0", "1.21.1-3.3.4", "1.21.2", false);
        check("26.2-3.4.0-kotlin.5+yanlingmtr.1.0.0-beta.1", "26.2-3.4.0-kotlin.5", "26.3", true);
        check("26.2-1.0.0-beta.1", "26.2-3.4.0-kotlin.5", "26.3", false);
        System.out.println("PASS: 8 real Fabric/Maven version-range checks; branded compatibility versions pass existing addon floors, naive resets fail");
    }

    private static void check(String version, String floor, String ceiling, boolean expected) throws Exception {
        boolean fabric = VersionPredicate.parse(">=" + floor + " <" + ceiling).test(Version.parse(version));
        boolean neo = VersionRange.createFromVersionSpec("[" + floor + "," + ceiling + ")")
                .containsVersion(new DefaultArtifactVersion(version));
        if (fabric != expected || neo != expected) {
            throw new AssertionError(version + ": Fabric=" + fabric + ", Maven=" + neo + ", expected=" + expected);
        }
    }
}
