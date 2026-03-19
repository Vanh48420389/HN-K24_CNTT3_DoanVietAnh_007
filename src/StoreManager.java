import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String category;

    public Product() {}

    public Product(String productId, String productName, double price, int quantity, String category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }

    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCategory(String category) { this.category = category; }

    public void inputData(Scanner sc){
        this.productId = inputProductID(sc);
        this.productName = inputProductName(sc);
        this.price = inputProductPrice(sc);
        this.quantity = inputProductQuantity(sc);
        this.category = inputProductCategory(sc);
    }

    public void displayData(){
        System.out.printf("%-10s %-15s %-10.2f %-10d %-10s\n",
                productId, productName, price, quantity, category);
    }

    public static String inputProductID(Scanner sc ){
        do {
            System.out.print("Nhap ma san pham: ");
            String id = sc.nextLine().trim();
            if (!id.isEmpty()) return id;
            System.out.println("Khong duoc de trong!");
        } while (true);
    }

    public static String inputProductName(Scanner sc ){
        do {
            System.out.print("Nhap ten san pham: ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) return name;
            System.out.println("Khong duoc de trong!");
        } while (true);
    }

    public static double inputProductPrice(Scanner sc ){
        do {
            System.out.print("Nhap gia: ");
            try {
                double price = Double.parseDouble(sc.nextLine());
                if (price >= 0) return price;
                System.out.println("Gia >= 0!");
            } catch (Exception e){
                System.out.println("Phai la so!");
            }
        } while (true);
    }

    public static int inputProductQuantity(Scanner sc ){
        do {
            System.out.print("Nhap so luong: ");
            try {
                int quantity = Integer.parseInt(sc.nextLine());
                if (quantity >= 0) return quantity;
                System.out.println("So luong >= 0!");
            } catch (Exception e){
                System.out.println("Phai la so nguyen!");
            }
        } while (true);
    }

    public static String inputProductCategory(Scanner sc ){
        do {
            System.out.print("Nhap loai: ");
            String category = sc.nextLine().trim();
            if (!category.isEmpty()) return category;
            System.out.println("Khong duoc de trong!");
        } while (true);
    }
}

class WareHouseBusiness {
    private static WareHouseBusiness instance;
    private final List<Product> productList;

    private WareHouseBusiness() {
        productList = new ArrayList<>();
    }

    public static WareHouseBusiness getInstance() {
        if (instance == null) {
            instance = new WareHouseBusiness();
        }
        return instance;
    }

    public void displayProduct(){
        if (productList.isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }

        System.out.printf("%-10s %-15s %-10s %-10s %-10s\n",
                "Ma", "Ten", "Gia", "SoLuong", "Loai");

        for (Product p : productList) {
            p.displayData();
        }
    }

    public boolean addProduct(Product product) {
        boolean isExist = productList.stream()
                .anyMatch(p -> p.getProductId().equalsIgnoreCase(product.getProductId()));

        if (isExist) {
            System.out.println("Ma da ton tai!");
            return false;
        }

        productList.add(product);
        System.out.println("Them thanh cong!");
        return true;
    }

    public void updateProduct(Scanner sc) {
        System.out.print("Nhap ma can sua: ");
        String id = sc.nextLine();

        for (Product p : productList) {
            if (p.getProductId().equalsIgnoreCase(id)) {
                System.out.println("Nhap thong tin moi:");
                p.setProductName(Product.inputProductName(sc));
                p.setPrice(Product.inputProductPrice(sc));
                p.setQuantity(Product.inputProductQuantity(sc));
                p.setCategory(Product.inputProductCategory(sc));
                System.out.println("Cap nhat OK!");
                return;
            }
        }
        System.out.println("Khong tim thay!");
    }

    public void deleteProduct(String id) {
        boolean removed = productList.removeIf(p -> p.getProductId().equalsIgnoreCase(id));
        System.out.println(removed ? "Xoa OK!" : "Khong ton tai!");
    }

    public void searchProductByName(String name) {
        List<Product> result = new ArrayList<>();

        for (Product p : productList) {
            if (p.getProductName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Khong tim thay!");
            return;
        }

        for (Product p : result) {
            p.displayData();
        }
    }

    public void lowStock() {
        for (Product p : productList) {
            if (p.getQuantity() < 5) {
                p.displayData();
            }
        }
    }

    public void sortByPrice() {
        productList.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("Da sap xep!");
    }
}

public class StoreManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WareHouseBusiness wh = WareHouseBusiness.getInstance();

        int choice;
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Hien thi san pham");
            System.out.println("2. Them san pham");
            System.out.println("3. Sua san pham");
            System.out.println("4. Xoa san pham");
            System.out.println("5. Tim kiem san pham");
            System.out.println("6. Sap het hang san pham");
            System.out.println("7. Sap xep gia san pham");
            System.out.println("8. Thoat");
            System.out.print("Chon: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhap so");
                continue;
            }

            switch (choice) {
                case 1:
                    wh.displayProduct();
                    break;

                case 2:
                    Product p = new Product();
                    p.inputData(sc);
                    wh.addProduct(p);
                    break;

                case 3:
                    wh.updateProduct(sc);
                    break;

                case 4:
                    System.out.print("Nhap ma can xoa: ");
                    wh.deleteProduct(sc.nextLine());
                    break;

                case 5:
                    System.out.print("Nhap ten: ");
                    wh.searchProductByName(sc.nextLine());
                    break;

                case 6:
                    wh.lowStock();
                    break;

                case 7:
                    wh.sortByPrice();
                    break;

                case 8:
                    System.out.println("Thoat chuong trinh");
                    break;
            }

        } while (true);
    }
}