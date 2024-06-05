import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class WestministerClient extends JFrame {
    private JPanel panel1, panel2, panel4, panel5;
    private JButton btnShoppingCart, btnAddToShoppingCart;
    private JComboBox<String> cmbProductType;
    private JTable tblProductList;
    private JScrollPane panel3;

    private JLabel lblSelectedProductDetails, lblProductId, lblGetProductId, lblCategory, lblGetCategory, lblName,
            lblGetName, lblSize, lblGetSize, lblColour, lblGetColour, lblAvailItem, lblGetAvailItem;


    ArrayList<Clothing> clothingList = new ArrayList<>();
    ArrayList<Electronics> electronicList = new ArrayList<>();
    ShoppingCart shoppingCart=new ShoppingCart();


    public WestministerClient() {

        // load data in to the array list
        WestminsterShoppingManager.loadDataIntoTheSystem(clothingList, 'C');
        WestminsterShoppingManager.loadDataIntoTheSystem(electronicList, 'E');


        // Set up the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Westminster Shopping centre");
        setSize(800, 610);

        // Create 5 panels
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JScrollPane();
        panel4 = new JPanel();
        panel5 = new JPanel();

        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel4.setLayout(new GridLayout(7, 2, 5, 5));
        panel4.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 100));
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Set layout manager to GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Create the horizontal group
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panel1)
                .addComponent(panel2)
                .addComponent(panel3)
                .addComponent(panel4)
                .addComponent(panel5));

        // Create the vertical group
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(panel1, 50, 50, 50)
                .addComponent(panel2, 80, 80, 80)
                .addComponent(panel3, 200, 200, 200)
                .addComponent(panel4, 200, 200, 200)
                .addComponent(panel5, 80, 80, 80));

        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);


        // Add  components to each panel

        btnShoppingCart = new JButton("Shopping Cart");
        btnShoppingCart.setPreferredSize(new Dimension(150, 30));
        panel1.add(btnShoppingCart);


        btnShoppingCart.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                shoppingCart.setVisible(true);
            });
        });

        cmbProductType = new JComboBox<>(new String[]{"All", "Electronic", "Clothes"});
        cmbProductType.setPreferredSize(new Dimension(200, 30));
        panel2.add(cmbProductType);

        cmbProductType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                loadProductsIntoTable();
            }
        });

        String columns[] = {"Product ID", "Name", "Category", "Price($)", "Info"};

        Object[][] data = {
                {"test", "test", "test", "test", "test"}
        };

        tblProductList = new JTable(new DefaultTableModel(data, columns));
        loadProductsIntoTable();
        viewAllDataOfProduct();
        tblProductList.setGridColor(Color.BLACK);

        tblProductList.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblProductList.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblProductList.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblProductList.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblProductList.getColumnModel().getColumn(4).setPreferredWidth(100);

        for (int i = 0; i < tblProductList.getColumnCount(); i++) {
            tblProductList.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(JLabel.CENTER);
                    return this;
                }
            });
        }

        tblProductList.setAutoCreateRowSorter(true);
        panel3.setViewportView(tblProductList);


        lblSelectedProductDetails = new JLabel("Selected Product-Details");
        lblSelectedProductDetails.setFont(new Font("DejaVu Sans - Plain", Font.BOLD, 15));

        lblProductId = new JLabel("Product ID :");
        lblGetProductId = new JLabel("000");

        lblCategory = new JLabel("Category :");
        lblGetCategory = new JLabel("000");

        lblName = new JLabel("Name :");
        lblGetName = new JLabel("000");

        lblSize = new JLabel("Size :");
        lblGetSize = new JLabel("000");

        lblColour = new JLabel("Colour :");
        lblGetColour = new JLabel("000");

        lblAvailItem = new JLabel("Available Items :");
        lblGetAvailItem = new JLabel("000");

        panel4.add(lblSelectedProductDetails);
        panel4.add(new JLabel());
        panel4.add(lblProductId);
        panel4.add(lblGetProductId);
        panel4.add(lblCategory);
        panel4.add(lblGetCategory);
        panel4.add(lblName);
        panel4.add(lblGetName);
        panel4.add(lblSize);
        panel4.add(lblGetSize);
        panel4.add(lblColour);
        panel4.add(lblGetColour);
        panel4.add(lblAvailItem);
        panel4.add(lblGetAvailItem);

        btnAddToShoppingCart = new JButton("Add to Shopping Cart");
        btnAddToShoppingCart.setPreferredSize(new Dimension(180, 30));
        panel5.add(btnAddToShoppingCart);

        btnAddToShoppingCart.addActionListener(e -> {
            addToCartItem();
        });



        // Center the JFrame on the screen
        setLocationRelativeTo(null);
    }

    public void addToCartItem() {
        String productId=lblGetProductId.getText();
        int availableItems= Integer.parseInt(lblGetAvailItem.getText());
        String productName=lblGetName.getText();
        double price;

        String colour=lblGetColour.getText();
        String size=lblGetSize.getText();
        String brandName=lblGetSize.getText();
        String warrantyPeriod=lblGetColour.getText();

        Clothing c;
        Electronics e;

        int selectedRow = tblProductList.getSelectedRow();
        if (selectedRow != -1) {
            price = (double) tblProductList.getValueAt(selectedRow, 3);
        } else{
            JOptionPane.showMessageDialog(null,"Please select a product to add to the cart","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (availableItems==0){
            JOptionPane.showMessageDialog(null,"No more items available","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }else if (availableItems<0){
            JOptionPane.showMessageDialog(null,"Invalid number of items","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }else {
            if (lblGetCategory.getText().equals("Clothing")) {
                for (Clothing clothing : clothingList) {
                    if (clothing.getProductId().equals(productId)) {
                        clothing.setNumberOfAvailableItems(clothing.getNumberOfAvailableItems() - 1);
                        break;
                    }
                }
                c = new Clothing(productId, productName, availableItems, price, size, colour);
                shoppingCart.cartItems.add(c);
                lblGetAvailItem.setText(String.valueOf(availableItems-1));
                shoppingCart.add(c);
                Main.currentUser.setOrderedCount(Main.currentUser.getOrderedCount() + 1);
                shoppingCart.calculateTotalCost();

            } else {
                for (Electronics electronic : electronicList) {
                    if (electronic.getProductId().equals(productId)) {
                        electronic.setNumberOfAvailableItems(electronic.getNumberOfAvailableItems() - 1);
                        break;
                    }
                }
                e = new Electronics(productId, productName, availableItems, price, brandName, warrantyPeriod);
                shoppingCart.cartItems.add(e);
                lblGetAvailItem.setText(String.valueOf(availableItems-1));
                shoppingCart.add(e);
                Main.currentUser.setOrderedCount(Main.currentUser.getOrderedCount() + 1);
                shoppingCart.calculateTotalCost();
            }

        }
        System.out.println(Main.currentUser.getUserName());
        System.out.println(Main.currentUser.getOrderedCount());
    }


    public void loadProductsIntoTable() {
        highlightLessAvailProduct();
        DefaultTableModel model = (DefaultTableModel) tblProductList.getModel();
        model.setRowCount(0); // Clear the table

        String selectedCategory = (String) cmbProductType.getSelectedItem();

        if (selectedCategory.equals("All") || selectedCategory.equals("Electronic")) {
            Collections.sort(electronicList, Comparator.comparing(Electronics::getProductId));
            for (Electronics e : electronicList) {
                model.addRow(new Object[]{e.getProductId(), e.getProductName(), "Electronics", e.getPrice(), e.getBrandName() + " , " + e.getWarrantyPeriod()});
            }
        }


        if (selectedCategory.equals("All") || selectedCategory.equals("Clothes")) {
            Collections.sort(clothingList, Comparator.comparing(Clothing::getProductId));
            for (Clothing c : clothingList) {
                model.addRow(new Object[]{c.getProductId(), c.getProductName(), "Clothing", c.getPrice(), c.getSize() + " , " + c.getColour()});
            }
        }
        highlightLessAvailProduct();
    }

    void highlightLessAvailProduct() {
        for (int i = 0; i < tblProductList.getColumnCount(); i++) {
            tblProductList.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(JLabel.CENTER);

                    // Check the availability of the product
                    int availability = getAvailableItems((String) table.getValueAt(row, 0));
                    if (availability <= 3) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(Color.BLACK);
                    }

                    return this;
                }
            });
        }
    }

    int getAvailableItems(String productId) {
        for (Clothing c : clothingList) {
            if (c.getProductId().equals(productId)) {
                return c.getNumberOfAvailableItems();
            }
        }

        for (Electronics e : electronicList) {
            if (e.getProductId().equals(productId)) {
                return e.getNumberOfAvailableItems();
            }
        }
        return 0;
    }

    public void viewAllDataOfProduct() {
        tblProductList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblProductList.getSelectedRow() != -1) {
                    int selectedRow = tblProductList.getSelectedRow();
                    lblGetProductId.setText(tblProductList.getValueAt(selectedRow, 0).toString());
                    lblGetName.setText(tblProductList.getValueAt(selectedRow, 1).toString());
                    lblGetCategory.setText(tblProductList.getValueAt(selectedRow, 2).toString());
                    String[] info = tblProductList.getValueAt(selectedRow, 4).toString().split(" , ");
                    if (lblGetCategory.getText().equals("Clothing")) {
                        lblSize.setText("Size :");
                        lblGetSize.setText(info[0]);
                        lblColour.setText("Colour :");
                        lblGetColour.setText(info[1]);
                    } else {
                        lblSize.setText("Brand :");
                        lblGetSize.setText(info[0]);
                        lblColour.setText("Warranty :");
                        lblGetColour.setText(info[1]);
                    }
                    lblGetAvailItem.setText(String.valueOf(getAvailableItems(lblGetProductId.getText())));
                }
            }
        });
    }
}


