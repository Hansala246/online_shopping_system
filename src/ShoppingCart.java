import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ShoppingCart extends JFrame{

    ArrayList<Object> cartItems = new ArrayList<>();

    private JScrollPane panel1;
    private JPanel panel2;
    private JTable tblCart;

    private JLabel lblTotal,lblGetTotal,lblFirstPurchaseDiscount,lblGetFirstPurchaseDiscount,
            lblThreeItemsDiscount,lblGetThreeItemsDiscount,lblFinalTotal,lblGetFinalTotal;



    public ShoppingCart() {
        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Shopping Cart");
        setSize(600, 400);


        panel1=new JScrollPane();
        panel2=new JPanel();

        panel2.setLayout(new GridLayout(4,2));
        panel2.setBorder(BorderFactory.createEmptyBorder(10,150,10,0));

        // Set layout manager to GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Create the horizontal group
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1)
                .addComponent(panel2));

        // Create the vertical group
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(panel1, 250, 250, 250)
                .addComponent(panel2, 150, 150, 150));

        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);

        String columns[] = {"Product", "Quantity", "Price"};

        Object[][] data = {};

        tblCart = new JTable(new DefaultTableModel(data, columns));
        tblCart.setGridColor(Color.BLACK);
        tblCart.setRowHeight(50);



        for (int i = 0; i < tblCart.getColumnCount(); i++) {
            tblCart.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(JLabel.CENTER);
                    return this;
                }
            });
        }

        tblCart.setAutoCreateRowSorter(true);
        panel1.setViewportView(tblCart);

        String space="     - ";
        lblTotal=new JLabel("Total");
        lblGetTotal=new JLabel(space+"0.0 $");

        lblFirstPurchaseDiscount=new JLabel("First Purchase Discount(10%)");
        lblGetFirstPurchaseDiscount=new JLabel(space+"0.0 $");

        lblThreeItemsDiscount=new JLabel("Three Items Discount(20%)");
        lblGetThreeItemsDiscount=new JLabel(space+"0.0 $");

        lblFinalTotal=new JLabel("Final Total");
        lblGetFinalTotal=new JLabel(space+"0.0 $");

        lblTotal.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));
        lblGetTotal.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));
        lblFirstPurchaseDiscount.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));
        lblGetFirstPurchaseDiscount.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));
        lblThreeItemsDiscount.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));
        lblGetThreeItemsDiscount.setFont(new Font("DejaVu Sans - Plain", Font.PLAIN, 12));

        panel2.add(lblTotal);
        panel2.add(lblGetTotal);
        panel2.add(lblFirstPurchaseDiscount);
        panel2.add(lblGetFirstPurchaseDiscount);
        panel2.add(lblThreeItemsDiscount);
        panel2.add(lblGetThreeItemsDiscount);
        panel2.add(lblFinalTotal);
        panel2.add(lblGetFinalTotal);

        pack();

        setLocationRelativeTo(null);


    }


    public void add(Object item) {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();

        if (item instanceof Clothing clothing) {
            String details = clothing.getProductId() + ", " + clothing.getProductName() + ", " + clothing.getColour() + ", " + clothing.getSize();
            int row = findMatchedQuantityRow(clothing.getProductId());
            if(row != -1){
                int quantity = Integer.parseInt(model.getValueAt(row, 1).toString());
                double price = Double.parseDouble(model.getValueAt(row, 2).toString());
                price=price*(quantity+1);
                model.setValueAt(quantity + 1, row, 1);
                model.setValueAt(price, row, 2);
            } else {
                model.addRow(new Object[]{details, 1, clothing.getPrice()});
            }
        } else if (item instanceof Electronics electronics) {
            String details = electronics.getProductId() + ", " + electronics.getProductName() + ", " + electronics.getWarrantyPeriod() + ", " + electronics.getBrandName();
            int row = findMatchedQuantityRow(electronics.getProductId());
            if(row != -1){
                int quantity = Integer.parseInt(model.getValueAt(row, 1).toString());
                double price = Double.parseDouble(model.getValueAt(row, 2).toString());
                price=price*(quantity+1);
                model.setValueAt(quantity + 1, row, 1);
                model.setValueAt(price, row, 2);
            } else {
                model.addRow(new Object[]{details, 1, electronics.getPrice()});
            }
        }


    }

    public int findMatchedQuantityRow(String productId) {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString().split(",")[0];
            if (id.equals(productId)) {
                return i;
            }
        }
        return -1; // Return -1 if no matching quantity is found
    }

    public void calculateTotalCost() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double price = Double.parseDouble(model.getValueAt(i, 2).toString());
            total += price;
        }

        double firstPurchaseDiscount = 0;
        if (Main.currentUser.getOrderedCount() == 0) {
            firstPurchaseDiscount = total * 0.1;
        }

        double threeItemsDiscount = 0;
        HashMap<String, Integer> categoryCount = new HashMap<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String category = model.getValueAt(i, 0).toString().split(",")[2];
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            if (categoryCount.get(category) == 3) {
                threeItemsDiscount = total * 0.2;
                break;
            }
        }

        double finalTotal = total - firstPurchaseDiscount - threeItemsDiscount;

        lblGetTotal.setText(String.format("%.2f $", total));
        lblGetFirstPurchaseDiscount.setText(String.format("%.2f $", firstPurchaseDiscount));
        lblGetThreeItemsDiscount.setText(String.format("%.2f $", threeItemsDiscount));
        lblGetFinalTotal.setText(String.format("%.2f $", finalTotal));
    }


}
