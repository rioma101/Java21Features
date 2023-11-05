package stringtemplate;

import static java.util.FormatProcessor.FMT;

public class StringTemplate {
    public static void main(String[] args) {
        System.out.println(interpolationWithConcatenation("Shit", 35.24890, "Celcius"));
        System.out.println(interpolationWithStringFormatter("Shit", 35.24890, "Celcius"));
        System.out.println(interpolationWithStringTemplate("Shit", 35.24890, "Celcius"));

        final int year = 2023;
        final String region = "West";
        final double totalAmount = 1000.0;
        final String paymentMethod = "Credit Card";
        final String orderStatus = "Shipped";
        final String productCategory = "Electronics";

        final String formattedQuery = queryWithStringFormatter(year, region, totalAmount, paymentMethod, orderStatus, productCategory);
        System.out.println(formattedQuery);

        final String templateQuery = queryWithStringTemplate(year, region, totalAmount, paymentMethod, orderStatus, productCategory);
        System.out.println(templateQuery);
    }

    public static String interpolationWithConcatenation(String feelsLike, double temperature, String unit) {
        return "{\n" +
                "\t\"feelsLike\": \"" + feelsLike + "\",\n" +
                "\t\"temperature\": \"" + String.format("%2.2f", temperature) + "\n" +
                "\t\"unit\": \"" + unit + "\",\n" +
                "}";
    }

    public static String interpolationWithStringFormatter(String feelsLike, double temperature, String unit) {
        String jsonTemplate = """
                {
                    "feelsLike": "%s",
                    "temperature": "%2.2f",
                    "unit": "%s"
                }""";
        return String.format(jsonTemplate, feelsLike, temperature, unit);
    }
    public static String interpolationWithStringTemplate(String feelsLike, double temperature, String unit) {
        return FMT. """
            {
                "feelsLike": "\{ feelsLike }",
                "temperature": "%2.2f\{ temperature }",
                "unit": "\{ unit }"
            }""" ;
    }

    public static String queryWithStringFormatter(int year, String region, double totalAmount, String paymentMethod, String orderStatus, String productCategory) {
        return """
            SELECT customers.customer_name, SUM(orders.order_amount) as total_order_amount
            FROM customers
            JOIN orders ON customers.customer_id = orders.customer_id
            JOIN products ON orders.product_id = products.product_id
            WHERE
                YEAR(orders.order_date) = %d
                AND customers.region = '%s'
                AND SUM(orders.order_amount) > %.2f
                AND customers.payment_method = '%s'
                AND orders.order_status = '%s'
                AND products.product_category = '%s'
            GROUP BY customers.customer_name
            ORDER BY total_order_amount DESC
            """.formatted(year, region, totalAmount, paymentMethod, orderStatus, productCategory);
    }

    public static String queryWithStringTemplate(int year, String region, double totalAmount, String paymentMethod, String orderStatus, String productCategory) {
        return FMT."""
            SELECT customers.customer_name, SUM(orders.order_amount) as total_order_amount
            FROM customers
            JOIN orders ON customers.customer_id = orders.customer_id
            JOIN products ON orders.product_id = products.product_id
            WHERE
                YEAR(orders.order_date) = \{year}
                AND customers.region = '\{region}'
                AND SUM(orders.order_amount) > %.2f\{totalAmount}
                AND customers.payment_method = '\{paymentMethod}'
                AND orders.order_status = '\{orderStatus}'
                AND products.product_category = '\{productCategory}'
            GROUP BY customers.customer_name
            ORDER BY total_order_amount DESC
            """;
    }



}
