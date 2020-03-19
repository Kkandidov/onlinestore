package org.astashonok.onlinestorebackend.testconfig;

import org.astashonok.onlinestorebackend.dto.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaticInitializerDTO {

    // Category object
    public static Category category1 = new Category(1, "Mobile phone", true);
    public static Category category2 = new Category(2, "Television", true);
    public static Category category3 = new Category(3, "Laptop", true);
    public static Category category4 = new Category(4, "Tablet", true);
    public static Category category5 = new Category(5, "Player", true);

    // Brand object
    public static Brand brand10 = new Brand(10,"Honor", "Honor is a smartphone brand owned by Huawei "
            + "Technologies. As part of the Huawei Consumer Business Group's dual-brand strategy, Honor provides " +
            "smartphone handsets targeting young consumers but has released tablet computers and wearable technology "
            + "as well", true);
    public static Brand brand1 = new Brand(1,"Samsung", "Samsung, South Korean company that is one of "
            + "the world’s largest producers of electronic devices. It produces about a fifth of South Korea’s total "
            + "exports", true);
    public static Brand brand3 = new Brand(3,"Xiaomi", "Xiaomi is a Chinese electronics company founded"
            + " in 2010 by Lei Jun. The company creates a wide range of products including hardware, software and "
            + "Internet services", true);

    // Role object
    public static Role role1 = new Role(1,"ADMIN", true);
    public static Role role2 = new Role(2,"USER", true);

    // User object
    public static User user1 = new User(1,"Ivan", "Ivanov", "ivan@gmail.com"
            , "$2y$12$i5iA/3OVxdeVLB4h5ttOSecMkd1E0Vj9ywhjL449OuemuD09buJvS", "+375296543218"
            , true, role1);
    public static User user2 = new User(2,"Petr", "Petrov", "petr@gmail.com"
            , "$2y$12$r4EhYmgRbDrbmfAMvE1usO/fY8yv1Z.Hp6D9OSIcYelIfjYxUj3e.", "375296543384"
            , true, role2);
    public static User user3 = new User(3,"Sergey", "Sergeev", "sergey@gmail.com"
            , "$2y$12$ZSE/h0gS.Mg.qZnuwzfCxuBd3D1qH3KeY4wL9qZEcAt0FQYNRrBIO", "+375-29-654-32-45"
            , true, role2);

    // Address object
    public static Address address11 = new Address(1, user2, "Platonov street", "", "Minsk"
            , "Minsk", "Belarus", "220034", false, true);
    public static Address address21 = new Address(2, user3, "Serdich street", "", "Minsk"
            , "Minsk", "Belarus", "220035", false, true);
    public static Address address12 = new Address(3, user2, "Platonov street", "", "Minsk"
            , "Minsk", "Belarus", "220034", true, false);
    public static Address address22 = new Address(4, user3, "Serdich street", "", "Minsk"
            , "Minsk", "Belarus", "220035", true, false);

    // Product object
    public static Product productEmpty = new Product();
    public static Product product1 = new Product(1,"HONOR 20 international version", "MAIN1581865663258"
            , brand10, 400, 10, true, category1);
    public static Product product2 = new Product(2,"Samsung Galaxy S10 G973", "MAIN1581866051337"
            , brand1, 710, 8, true, category1);
    public static Product product3 = new Product(3,"Xiaomi Mi 9T", "MAIN1581866964489"
            , brand3, 343, 6, true, category1);

    // Description object
    public static Description description1 = new Description(product1, "6.26\" IPS (1080x2340)", "ice white"
            , "HiSilicon Kirin 980", "32 MP", "48 MP"
            , "6 GB RAM / 128 GB flash memory", "Li-ion 3 750 mAh", "IPS"
            , "16 million", "Bluetooth / Wifi / Nfc");
    public static Description description2 = new Description(product2, "6.1\" AMOLED (1440x3040)", "black"
            , "Exynos 9820", "10 MP", "12 MP"
            , "8 GB RAM / 128 GB flash memory", "Li-ion 3 400 mAh", "AMOLED"
            , "16 million", "Bluetooth / Wifi / Nfc");
    public static Description description3 = new Description(product3, "6.39\" AMOLED (1080x2340)", "blue"
            , "Qualcomm Snapdragon 730", "20 MP", "48 MP"
            , "6 GB RAM / 64 GB flash memory", "Li-pol 4,000 mAh", "AMOLED"
            , "16 million", "Bluetooth / Wifi / Nfc");

    // View object
    public static View view11 = new View("PRD1581865664262", product1);
    public static View view12 = new View("PRD1581865665263", product1);
    public static View view13 = new View("PRD1581865666263", product1);
    public static View view14 = new View("PRD1581865667263", product1);
    public static View view15 = new View("PRD1581865668263", product1);
    public static View view21 = new View("PRD1581866052337", product2);
    public static View view22 = new View("PRD1581866053337", product2);
    public static View view23 = new View("PRD1581866054337", product2);
    public static View view24 = new View("PRD1581866055338", product2);
    public static View view25 = new View("PRD1581866056338", product2);
    public static View view31 = new View("PRD1581866965493", product3);
    public static View view32 = new View("PRD1581866966495", product3);

    // Cart object
    public static Cart cart2 = new Cart(user2, 1510, 2);
    public static Cart cart3 = new Cart(user3, 1029, 1);

    // CartItem object
    public static CartItem cartItem21 = new CartItem(cart2, 800, product1, 2, 400
            , true);
    public static CartItem cartItem22 = new CartItem(cart2, 710, product2, 1, 710
            , true);
    public static CartItem cartItem31 = new CartItem(cart3, 1029, product3, 3, 343
            , true);

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
    static String dateInString2 = "2020-03-03 10:37:22";
    static String dateInString3 = "2020-03-05 13:35:21";
    static Date date2;
    static Date date3;

    static {
        try {
            date2 = formatter.parse(dateInString2);
            date3 = formatter.parse(dateInString3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Order object
    public static Order order2 = new Order(1,user2, 1510, 3, address11, address12, date2);
    public static Order order3 = new Order(2,user3, 1029, 3, address21, address22, date3);

    // OrderItem object
    public static OrderItem orderItem11 = new OrderItem(order2, 800, product1, 2, 400);
    public static OrderItem orderItem12 = new OrderItem(order2, 710, product2, 1, 710);
    public static OrderItem orderItem21 = new OrderItem(order3, 1029, product3, 3, 343);

    public StaticInitializerDTO() throws ParseException {
    }
}
