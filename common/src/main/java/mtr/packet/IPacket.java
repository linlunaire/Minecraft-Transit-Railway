package mtr.packet;

import mtr.MTR;
import net.minecraft.resources.Identifier;

public interface IPacket {

	Identifier PACKET_VERSION_CHECK = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_version_check");

	Identifier PACKET_OPEN_DASHBOARD_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_dashboard_screen");
	Identifier PACKET_OPEN_PIDS_CONFIG_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_pids_config_screen");
	Identifier PACKET_OPEN_ARRIVAL_PROJECTOR_CONFIG_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_arrival_projector_config_screen");
	Identifier PACKET_OPEN_RAILWAY_SIGN_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_railway_sign_screen");
	Identifier PACKET_OPEN_TICKET_MACHINE_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_ticket_machine_screen");
	Identifier PACKET_OPEN_TRAIN_SENSOR_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_train_sensor_screen");
	Identifier PACKET_OPEN_LIFT_TRACK_FLOOR_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_lift_track_floor_screen");
	Identifier PACKET_OPEN_LIFT_CUSTOMIZATION_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_lift_customization_screen");
	Identifier PACKET_OPEN_RESOURCE_PACK_CREATOR_SCREEN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_open_resource_pack_creator_screen");

	Identifier PACKET_ANNOUNCE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_announce");
	Identifier PACKET_USE_TIME_AND_WIND_SYNC = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_use_time_and_wind_sync");

	Identifier PACKET_CREATE_RAIL = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_create_rail");
	Identifier PACKET_CREATE_SIGNAL = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_create_signal");
	Identifier PACKET_REMOVE_NODE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_remove_node");
	Identifier PACKET_REMOVE_LIFT_FLOOR_TRACK = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_remove_lift_floor_track");
	Identifier PACKET_REMOVE_RAIL = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_remove_rail");
	Identifier PACKET_REMOVE_SIGNALS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_remove_signals");
	Identifier PACKET_REMOVE_RAIL_ACTION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_remove_rail_action");

	Identifier PACKET_GENERATE_PATH = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_generate_path");
	Identifier PACKET_CLEAR_TRAINS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_clear_trains");
	Identifier PACKET_SIGN_TYPES = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_sign_types");
	Identifier PACKET_DRIVE_TRAIN = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_drive_train");
	Identifier PACKET_PRESS_LIFT_BUTTON = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_press_lift_button");
	Identifier PACKET_ADD_BALANCE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_add_balance");
	Identifier PACKET_PIDS_UPDATE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_pids_update");
	Identifier PACKET_ARRIVAL_PROJECTOR_UPDATE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_arrival_projector_update");
	Identifier PACKET_CHUNK_S2C = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_chunk_s2c");

	Identifier PACKET_UPDATE_STATION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_station");
	Identifier PACKET_UPDATE_PLATFORM = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_platform");
	Identifier PACKET_UPDATE_SIDING = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_siding");
	Identifier PACKET_UPDATE_ROUTE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_route");
	Identifier PACKET_UPDATE_DEPOT = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_depot");
	Identifier PACKET_UPDATE_LIFT = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_lift");

	Identifier PACKET_DELETE_STATION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_delete_station");
	Identifier PACKET_DELETE_PLATFORM = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_delete_platform");
	Identifier PACKET_DELETE_SIDING = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_delete_siding");
	Identifier PACKET_DELETE_ROUTE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_delete_route");
	Identifier PACKET_DELETE_DEPOT = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_delete_depot");

	Identifier PACKET_WRITE_RAILS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "write_rails");
	Identifier PACKET_UPDATE_TRAINS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_trains");
	Identifier PACKET_DELETE_TRAINS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "delete_trains");
	Identifier PACKET_UPDATE_LIFTS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_lifts");
	Identifier PACKET_DELETE_LIFTS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "delete_lifts");
	Identifier PACKET_UPDATE_TRAIN_PASSENGERS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_train_passengers");
	Identifier PACKET_UPDATE_TRAIN_PASSENGER_POSITION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_train_passenger_position");
	Identifier PACKET_UPDATE_LIFT_PASSENGERS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_lift_passengers");
	Identifier PACKET_UPDATE_LIFT_PASSENGER_POSITION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_lift_passenger_position");
	Identifier PACKET_UPDATE_ENTITY_SEAT_POSITION = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_entity_seat_position");
	Identifier PACKET_UPDATE_RAIL_ACTIONS = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_rail_actions");
	Identifier PACKET_UPDATE_SCHEDULE = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "update_schedule");
	Identifier PACKET_UPDATE_TRAIN_SENSOR = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_train_announcer");
	Identifier PACKET_UPDATE_LIFT_TRACK_FLOOR = Identifier.fromNamespaceAndPath(MTR.MOD_ID, "packet_update_lift_track_floor");

	int MAX_PACKET_BYTES = 1048576;
}
