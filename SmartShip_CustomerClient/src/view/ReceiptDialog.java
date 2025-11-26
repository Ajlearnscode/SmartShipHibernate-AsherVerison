package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ReceiptDialog extends JDialog {

    private static final long serialVersionUID = 1L;

	public ReceiptDialog(JFrame parent, Map<String, String> receipt) {
        super(parent, "Payment Receipt", true);

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Payment Receipt", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Main info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        addLabel(infoPanel, "Receipt No:", receipt.get("paymentId"));
        addLabel(infoPanel, "Invoice ID:", receipt.get("invoiceId"));
        addLabel(infoPanel, "Tracking Number:", receipt.get("trackingNumber"));
        addLabel(infoPanel, "Customer:", receipt.get("customerName"));
        addLabel(infoPanel, "Email:", receipt.get("email"));
        addLabel(infoPanel, "Address:", receipt.get("address"));
        addLabel(infoPanel, "Payment Method:", receipt.get("paymentMethod"));
        addLabel(infoPanel, "Amount:", "$" + receipt.get("amount"));
        addLabel(infoPanel, "Date & Time:", receipt.get("paymentDate"));
        addLabel(infoPanel, "Shipment Status:", receipt.get("status"));

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(null);

        // OK button
        JButton okBtn = new JButton("Close");
        okBtn.setFont(new Font("Arial", Font.BOLD, 14));
        okBtn.addActionListener(e -> dispose());
        okBtn.setBackground(new Color(163, 67, 53));
        okBtn.setForeground(Color.WHITE);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(okBtn);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label + " " + value);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(lbl);
    }
}
