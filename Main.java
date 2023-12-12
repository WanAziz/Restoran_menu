import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

abstract class MenuItem {
    protected String name;
    protected int price;
    protected String category;

    public MenuItem(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public abstract void displayMenu();

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}

class Food extends MenuItem {
    private String details;

    public Food(String name, int price, String details) {
        super(name, price, "makanan");
        this.details = details;
    }

    @Override
    public void displayMenu() {
        System.out.println(" - " + name + " | Detail : " + details +
            " | Harga: " + price);
    }

    public String getDetails() {
        return details;
    }
}

class Drink extends MenuItem {
    private String details;

    public Drink(String name, int price, String details) {
        super(name, price, "minuman");
        this.details = details;
    }

    @Override
    public void displayMenu() {
        System.out.println(
            " - " + name + " | Detail : " + details + " | Harga : " + price);
    }

    public String getDetails() {
        return details;
    }
}

class Discount extends MenuItem {
    private String discount;

    public Discount(String name, int price, String discount) {
        super(name, price, "Discount");
        this.discount = discount;
    }

    @Override
    public void displayMenu() {
        System.out.println(
            " - " + name + " | Discount : " + discount);
    }

    public String getDiscount() {
        return discount;
    }
}

class OrderItem {
    private String name;
    private int quantity;
    private int price;

    public OrderItem(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}

class Menu {
    private ArrayList < MenuItem > menuItems = new ArrayList < > ();

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public void removeAllMenuItem() {
        menuItems.clear();
    }

    public void displayMenu(int number) {
        System.out.println("========== Restaurant Menu ==========");
        System.out.println("Makanan :");
        for (MenuItem item: menuItems) {
            if (item.getCategory().toLowerCase().indexOf("makanan") >= 0) {
                item.displayMenu();
            }
        }

        System.out.println("Minuman :");
        for (MenuItem item: menuItems) {
            if (item.getCategory().toLowerCase().indexOf("minuman") >= 0) {
                item.displayMenu();
            }
        }

        if (number == 0) {
            System.out.println("Diskon :");
            for (MenuItem item: menuItems) {
                if (item.getCategory().toLowerCase().indexOf("discount") >= 0) {
                    item.displayMenu();
                }
            }
        }
    }

    public boolean cekMenu(String input) {
        String namaMenu;
        boolean cek = false;

        for (int i = 0; i < menuItems.size(); i++) {
            namaMenu = menuItems.get(i).getName();
            if (namaMenu.toLowerCase().replaceAll("\\s", "").indexOf(
                    input.toLowerCase().replaceAll("\\s", "")) ==
                0) {
                cek = true;
            }
        }
        return cek;
    }

    public int cekHarga(String input) {
        String namaMenu;
        int harga = 0;

        for (int i = 0; i < menuItems.size(); i++) {
            namaMenu = menuItems.get(i).getName();
            if (namaMenu.toLowerCase().replaceAll("\\s", "").indexOf(
                    input.toLowerCase().replaceAll("\\s", "")) ==
                0) {
                harga = menuItems.get(i).getPrice();
            }
        }
        return harga;
    }

    public String cekKategori(String input) {
        String namaMenu;
        String kategori = "";

        for (int i = 0; i < menuItems.size(); i++) {
            namaMenu = menuItems.get(i).getName();
            if (namaMenu.toLowerCase().replaceAll("\\s", "").indexOf(
                    input.toLowerCase().replaceAll("\\s", "")) ==
                0) {
                kategori = menuItems.get(i).getCategory();
            }
        }
        return kategori;
    }
}

class Order {
    private ArrayList < OrderItem > orderedItems = new ArrayList < > ();

    public void addOrder(OrderItem item) {
        orderedItems.add(item);
    }

    public void removeOrder(int item) {
        orderedItems.remove(item);
    }

    public int getArraySize() {
        return orderedItems.size();
    }

    public void displayOrder() {
        System.out.println("=== Daftar Pesanan ===");
        if (orderedItems.size() != 0) {
            for (int i = 0; i < orderedItems.size(); i++) {
                System.out.println((i + 1) + ". " + orderedItems.get(i).getName() + " : " + orderedItems.get(i).getQuantity());
            }
        } else {
            System.out.println("Pesan Makanan / Minuman Dahulu");
        }
    }

    public void createStruk() {
        if (orderedItems.size() != 0) {
            int totalBiaya = 0;
            double discount = 0;
            double pajak = 0;
            String pesanan = "";
            String discount1 = "";
            String discount2 = "";

            for (int i = 0; i < orderedItems.size(); i++) {
                totalBiaya += (orderedItems.get(i).getQuantity() *
                    orderedItems.get(i).getPrice());
            }

            for (int i = 0; i < orderedItems.size(); i++) {
                pesanan += " - " + orderedItems.get(i).getName() + " : " + orderedItems.get(i).getQuantity() + "\n";
            }

            if (totalBiaya > 50000) {
                discount1 = "Selamat! Anda mendapatkan penawaran beli satu gratis satu untuk minuman\n";
            }

            if (totalBiaya > 100000) {
                discount = totalBiaya * 0.1;
                discount2 = "Selamat! Anda mendapatkan diskon 10% : " + discount + "\n";
            }

            pajak = (totalBiaya - discount) * 0.1;

            String struk = "===========================\nStruk Pesanan\n===========================\n" + pesanan + "===========================\nTotal Biaya : " + totalBiaya + "\n===========================\n" + discount1 + discount2 + "===========================\nPajak (10%) : " + pajak + "\nBiaya Pelayanan : 20.000\n===========================\nBiaya Akhir = " + (totalBiaya - discount + pajak);

            try {
                FileWriter myWriter = new FileWriter("struk.txt");
                myWriter.write(struk);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } else {
            System.out.println("Pesan Makanan / Minuman Dahulu");
        }
    }
}

public class Main {
    static Menu restaurantMenu = new Menu();
    static Order customerOrder = new Order();
    static Scanner scanner = new Scanner(System.in);
    // pelanggan menu
    private static void loadMenu(String filename) {
        String data = "";
        try {
            File file = new File(filename + ".txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String nameMenu = "";
        int priceMenu = 0;
        String detailsMenu = "";

        while (true) {
            if (data.indexOf("|") == -1) {
                break;
            }

            nameMenu = data.substring(0, data.indexOf(":"));
            priceMenu = Integer.parseInt(data.substring(data.indexOf(":") + 1, data.indexOf(";")));
            detailsMenu = data.substring(data.indexOf(";") + 1, data.indexOf("|"));

            if (filename.toLowerCase() == "makanan") {
                restaurantMenu.addMenuItem(new Food(nameMenu, priceMenu, detailsMenu));
            }

            if (filename.toLowerCase() == "minuman") {
                restaurantMenu.addMenuItem(new Drink(nameMenu, priceMenu, detailsMenu));
            }

            data = data.substring(data.indexOf("|") + 1, data.length());
        }
    }

    private static void addOrder() {
        String choice;
        String nameOrder;
        int quantityOrder;
        int priceOrder;

        restaurantMenu.displayMenu(0);

        System.out.println(
            "Memilih pesanan (FORMAT '[nama menu] = [jumlah pesanan]', ketik 'selesai' untuk mengakhiri pesanan)");
        while (true) {
            choice = scanner.nextLine().trim();
            if (choice != "" && choice.indexOf(" = ") != -1 &&
                restaurantMenu.cekMenu(choice.substring(0, choice.indexOf(" = "))) ==
                true) {
                nameOrder = choice.substring(0, choice.indexOf(" = "));
                quantityOrder = Integer.parseInt(choice.substring(
                    (choice.indexOf(" = ") + 3), choice.length()));
                priceOrder = restaurantMenu.cekHarga(choice.substring(0, choice.indexOf(" = ")));

                if (quantityOrder > 0) {
                    customerOrder.addOrder(new OrderItem(nameOrder, quantityOrder, priceOrder));
                } else {
                    System.out.println("Jumlah Pesanan Salah");
                }
            } else {
                if (choice.indexOf("selesai") == -1 ||
                    choice.indexOf("selesai") > 0) {
                    System.out.println("Format Pesanan Salah");
                } else {
                    break;
                }
            }
        }
    }

    private static void removeOrder() {
        customerOrder.displayOrder();

        System.out.print("Masukkan nomor pesanan yang ingin dihapus: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= customerOrder.getArraySize()) {
            customerOrder.removeOrder(choice - 1);
            System.out.println("Pesanan telah dihapus.");
        } else {
            System.out.println(
                "Pilihan Pesanan tidak valid. Silakan coba lagi.");
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("=== Restoran Ayam Gila ===");
            System.out.println("1. Lihat Menu");
            System.out.println("2. Lihat Pesanan");
            System.out.println("3. Tambah Pesanan");
            System.out.println("4. Hapus Pesanan");
            System.out.println("5. Cetak Struk");
            System.out.println("6. Keluar");
            System.out.print("Pilih nomer menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                restaurantMenu.displayMenu(0);
                break;
            case 2:
                customerOrder.displayOrder();
                break;
            case 3:
                addOrder();
                break;
            case 4:
                removeOrder();
                break;
            case 5:
                customerOrder.createStruk();
                break;
            case 6:
                return;
            default:
                System.out.println(
                    "Pilihan tidak valid. Silakan coba lagi.");
                break;
            }
        }
    }

    // manajemen menu
    private static void addMenu() {
        System.out.print("Masukkan nama menu: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan harga menu: ");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Masukkan deskripsi menu: ");
        String details = scanner.nextLine();
        System.out.print("Masukkan kategory menu (Makanan / Minuman): ");
        String category = scanner.nextLine().trim();

        String input = name + ":" + price + ";" + details + "|";

        if (category.toLowerCase().indexOf("makanan") == 0 || category.toLowerCase().indexOf("minuman") == 0) {
            try {
                FileWriter myWriter = new FileWriter(category.toLowerCase() + ".txt", true);
                myWriter.write(input);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            restaurantMenu.removeAllMenuItem();
            loadMenu("makanan");
            loadMenu("minuman");
        }

    }

    private static void deleteMenu() {
        restaurantMenu.displayMenu(1);

        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String choice = scanner.nextLine().trim();
        String data = "";
        String temp = "";

        if (restaurantMenu.cekMenu(choice)) {
            String nameMenu = "";

            try {
                File file = new File(restaurantMenu.cekKategori(choice) + ".txt");
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine();
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            while (true) {
                if (data.indexOf("|") == -1) {
                    break;
                }

                nameMenu = data.substring(0, data.indexOf(":"));
                if (nameMenu.toLowerCase().replaceAll("\\s", "").indexOf(choice.toLowerCase().replaceAll("\\s", "")) == -1) {
                    temp += data.substring(0, data.indexOf("|") + 1);
                    data = data.substring(data.indexOf("|") + 1, data.length());
                } else {
                    data = data.substring(data.indexOf("|") + 1, data.length());
                }
            }

        } else {
            System.out.println("Pilihan menu salah");
        }

        try {
            FileWriter myWriter = new FileWriter(restaurantMenu.cekKategori(choice) + ".txt");
            myWriter.write(temp);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        restaurantMenu.removeAllMenuItem();
        loadMenu("makanan");
        loadMenu("minuman");
    }

    private static void manajemenMenu() {
        while (true) {
            System.out.println("=== Restoran Ayam Gila ===");
            System.out.println("1. Lihat Menu");
            System.out.println("2. Menambah Menu");
            System.out.println("3. Menghapus Menu");
            System.out.println("4. Keluar");
            System.out.print("Pilih nomer menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                restaurantMenu.displayMenu(1);
                break;
            case 2:
                addMenu();
                break;
            case 3:
                deleteMenu();
                break;
            case 4:
                return;
            default:
                System.out.println(
                    "Pilihan tidak valid. Silakan coba lagi.");
                break;
            }
        }
    }

    public static void main(String args[]) {
        restaurantMenu.addMenuItem(new Discount("Makin Kenyang", 100000, "Potongan harga 10% apabila pembelian lebih dari 100.000"));
        restaurantMenu.addMenuItem(new Discount("Anti Haus", 50000, "Penawaran beli satu gratis satu untuk minuman apabila pembelian lebih dari 50.000"));

        loadMenu("makanan");
        loadMenu("minuman");

        while (true) {
            System.out.println("=== Restoran Ayam Gila ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Menu Manajemen");
            System.out.println("3. Keluar");
            System.out.print("Pilih nomer menu : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
                customerMenu();
                break;
            case 2:
                manajemenMenu();
                break;
            case 3:
                System.out.println(
                    "Terima kasih telah menggunakan aplikasi Restoran Ayam Gila.");
                System.exit(0);
            default:
                System.out.println(
                    "Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}