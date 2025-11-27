package network_server;

import database.DatabaseHelper;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Command interface that all command classes implement. Each command knows how
 * to execute itself by reading data from the client and returning a result.
 */
interface Command {
	Object execute(ObjectInputStream in) throws Exception;
}

/**
 * This class holds all the different commands our server can handle. Each
 * command is a separate inner class that does one specific task.
 */
public class Commands {

	/**
	 * REGISTER Command - creates a new user account Reads: username, password,
	 * email, phone, address Returns: success/failure message or the new user object
	 */
	public static class RegisterCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			// Read all the registration info the client sent
			String username = (String) in.readObject();
			String password = (String) in.readObject();
			String email = (String) in.readObject();
			String phone = (String) in.readObject();
			String address = (String) in.readObject();
			String role = (String) in.readObject();

			// Pass it to the database to create the account
			return DatabaseHelper.registerUser(username, password, email, phone, address, role);
		}
	}

	/**
	 * LOGIN Command - authenticates a user Reads: username, password Returns: user
	 * object if successful, null if login fails
	 */
	public static class LoginCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			String username = (String) in.readObject();
			String password = (String) in.readObject();
			return DatabaseHelper.login(username, password);
		}
	}

	/**
	 * GET CUSTOMER INFO Command - retrieves a user's profile Reads: userId Returns:
	 * customer information object
	 */
	public static class GetCustomerInfoCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			return DatabaseHelper.getCustomerInfo(userId);
		}
	}

	/**
	 * UPDATE CUSTOMER INFO Command - lets users edit their profile Reads: userId,
	 * email, phone, address Returns: success/failure indicator
	 */
	public static class UpdateCustomerInfoCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			String email = (String) in.readObject();
			String phone = (String) in.readObject();
			String address = (String) in.readObject();
			return DatabaseHelper.updateCustomerInfo(userId, email, phone, address);
		}
	}

	/**
	 * GET CUSTOMER ORDERS Command - shows all shipments for a customer Reads:
	 * userId Returns: list of all shipments this customer has made
	 */
	public static class GetCustomerOrdersCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			return DatabaseHelper.getCustomerOrders(userId);
		}
	}

	/**
	 * MAKE PAYMENT Command - processes payment for a shipment Reads: shipmentId,
	 * paymentMethod (e.g. "credit card", "cash") Returns: payment confirmation or
	 * receipt
	 */

	/**
	 * GET PENDING SHIPMENTS Command - for managers to see what needs delivery
	 * Reads: nothing Returns: list of all shipments waiting to be assigned to a
	 * vehicle
	 */
	public static class GetPendingShipmentsCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			return DatabaseHelper.getPendingShipments();
		}
	}

	/**
	 * ASSIGN SHIPMENT Command - manager assigns a shipment to a delivery vehicle
	 * Reads: shipmentId, vehicleId Returns: confirmation that assignment was
	 * successful
	 */
	public static class AssignShipmentCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int shipmentId = (int) in.readObject();
			int vehicleId = (int) in.readObject();
			return DatabaseHelper.assignShipmentToVehicle(shipmentId, vehicleId);
		}
	}

	/**
	 * GET DRIVER DELIVERIES Command - shows driver their assigned deliveries Reads:
	 * userId (the driver's id) Returns: list of shipments this driver needs to
	 * deliver
	 */
	public static class GetDriverDeliveriesCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			return DatabaseHelper.getDriverDeliveries(userId);
		}
	}

	/**
	 * UPDATE DELIVERY STATUS Command - driver updates where package is Reads:
	 * trackingNumber, status (e.g. "out for delivery", "delivered") Returns:
	 * confirmation that status was updated
	 */
	public static class UpdateDeliveryStatusCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			String trackingNumber = (String) in.readObject();
			String status = (String) in.readObject();
			return DatabaseHelper.updateDeliveryStatus(trackingNumber, status);
		}
	}

	/**
	 * GENERATE DAILY REPORT Command - creates summary of the day's activity Reads:
	 * nothing Returns: report object with stats like total deliveries, revenue,
	 * etc.
	 */
	public static class GenerateDailyReportCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			return DatabaseHelper.generateDailyReport();
		}
	}

	/**
	 * TRACK PACKAGE Command - Customer tracks their package Reads: trackingNumber
	 * Returns: shipment details map
	 */
	public static class TrackPackageCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			String trackingNumber = (String) in.readObject();
			return DatabaseHelper.getShipmentByTrackingNumber(trackingNumber);
		}
	}

	/**
	 * GET CUSTOMER INVOICES Command - Retrieve all invoices for a customer Reads:
	 * userId Returns: list of invoice maps
	 */
	public static class GetCustomerInvoicesCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			return DatabaseHelper.getCustomerInvoices(userId);
		}
	}

	/**
	 * MAKE PAYMENT Command - Process payment for an invoice Reads: invoiceId,
	 * amount, paymentMethod Returns: success/failure
	 */
	public static class MakePaymentCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int invoiceId = (int) in.readObject();
			double amount = (double) in.readObject();
			String paymentMethod = (String) in.readObject();
			return DatabaseHelper.makeInvoicePayment(invoiceId, amount, paymentMethod);
		}
	}

	/**
	 * CREATE SHIPMENT Command Now also stores recipient address in shipments table
	 * Reads: userId, senderInfo, recipientInfo, weight, dimensions, type, zone,
	 * recipientAddress Returns: tracking number
	 */
	public static class CreateShipmentCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int userId = (int) in.readObject();
			String senderInfo = (String) in.readObject();
			String recipientInfo = (String) in.readObject();
			double weight = (double) in.readObject();
			String dimensions = (String) in.readObject();
			String type = (String) in.readObject();
			int zone = (int) in.readObject();
			String recipientAddress = (String) in.readObject();
			return DatabaseHelper.createShipment(userId, senderInfo, recipientInfo, weight, dimensions, type, zone,
					recipientAddress);
		}
	}

	/**
	 * MAKE PAYMENT AND GET RECEIPT Command Processes payment for an invoice and
	 * returns receipt details Reads: invoiceId, amount, paymentMethod Returns: Map
	 * containing receipt information
	 */
	public static class MakePaymentAndGetReceiptCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			int invoiceId = (int) in.readObject();
			double amount = (double) in.readObject();
			String paymentMethod = (String) in.readObject();

			System.out.println("Processing payment for invoice: " + invoiceId);

			// Make the payment (transactional in DatabaseHelper)
			boolean paymentSuccess = DatabaseHelper.makeInvoicePayment(invoiceId, amount, paymentMethod);

			if (!paymentSuccess) {
				System.err.println("Payment transaction failed for invoice: " + invoiceId);
				return null;
			}

			System.out.println("Payment successful. Building receipt...");

			// Retrieve invoice and shipment details
			Map<String, String> receiptDetails = DatabaseHelper.getInvoiceAndShipmentDetails(invoiceId);

			if (receiptDetails == null) {
				System.err.println("Failed to retrieve receipt details for invoice: " + invoiceId);
				return null;
			}

			// Build complete receipt map
			Map<String, String> receipt = new HashMap<>();
			receipt.put("paymentId", "PAY" + System.currentTimeMillis());
			receipt.put("invoiceId", String.valueOf(invoiceId));
			receipt.put("trackingNumber", receiptDetails.get("trackingNumber"));
			receipt.put("customerName", receiptDetails.get("customerName"));
			receipt.put("email", receiptDetails.get("email"));
			receipt.put("address", receiptDetails.get("address"));
			receipt.put("paymentMethod", paymentMethod);
			receipt.put("amount", String.format("%.2f", amount));
			receipt.put("paymentDate",
					new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
			receipt.put("status", receiptDetails.get("status"));

			System.out.println("Receipt built successfully");
			return receipt;
		}
	}

	// NEW ADDITIONS
	/**
	 * GET AVAILABLE DRIVERS Command Fetches a list of active drivers for the combo
	 * box Reads: Nothing Returns: List<String> of drivers
	 */
	public static class GetAvailableDriversCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			// Calls the method we added to DatabaseHelper
			return DatabaseHelper.getAvailableDrivers();
		}
	}

	/**
	 * GET AVAILABLE VEHICLES Command Fetches a list of available vehicles for the
	 * combo box Reads: Nothing Returns: List<String> of vehicles
	 */
	public static class GetAvailableVehiclesCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			// Calls the method we added to DatabaseHelper
			return DatabaseHelper.getAvailableVehicles();
		}
	}

	/**
	 * UPDATE SHIPMENT ASSIGNMENT Command Updates the driver, vehicle, and route for
	 * a specific shipment Reads: trackingNumber, driver, vehicle, route Returns:
	 * boolean (success/failure)
	 */
	/*
	 * public static class UpdateShipmentAssignmentCommand implements Command {
	 * 
	 * @Override public Object execute(ObjectInputStream in) throws Exception { //
	 * Read the data sent by ClerkCommandController in the exact order it was //
	 * written String trackingNumber = (String) in.readObject(); String driver =
	 * (String) in.readObject(); String vehicle = (String) in.readObject(); String
	 * route = (String) in.readObject();
	 * 
	 * // Pass to DatabaseHelper to perform the update return
	 * DatabaseHelper.updateShipmentAssignment(trackingNumber, driver, vehicle,
	 * route); } }
	 */

	// ##########################################################################################################################################################################################################################################################
	// NEW ADDITIONS -- November 27, 2025 (12:12 AM) - Asher Maxwell

	/**
	 * shows all shipments for every customer Returns: list of all shipments of all
	 * customers
	 */
	public static class GetAllOrdersCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			return DatabaseHelper.getAllOrders();
		}
	}

	public static class UpdateShipmentCommand implements Command {

		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			// Read fields sent from client
			String trackingNumber = (String) in.readObject();
			String newStatus = (String) in.readObject();
			String newPaymentStatus = (String) in.readObject();

			// Update database
			boolean success = DatabaseHelper.updateShipment(trackingNumber, newStatus, newPaymentStatus);

			// Return result to client
			return success;
		}
	}

	/**
	 * Assign shipment - assigns driver, vehicle, and route
	 */
	public static class AssignShipmentFullCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			String trackingNumber = (String) in.readObject();
			Integer driverId = (Integer) in.readObject();
			Integer vehicleId = (Integer) in.readObject();
			String route = (String) in.readObject();

			return DatabaseHelper.assignShipment(trackingNumber, driverId, vehicleId, route);
		}
	}

	/**
	 * get shipment assignment commands
	 */
	public static class GetShipmentAssignmentsCommand implements Command {
		@Override
		public Object execute(ObjectInputStream in) throws Exception {
			return DatabaseHelper.getShipmentAssignments();
		}
	}
	// END OF ADDITIONS
//##########################################################################################################################################################################################################################################################

}