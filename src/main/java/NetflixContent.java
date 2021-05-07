import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;


public class NetflixContent {
    private static Map<String, Integer> colHeading=new HashMap<>();
    private static List<List<String>> netflixContent=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Scanner scn=new Scanner(System.in);
        System.out.println("Hello! user please select option which you want to perform");
        System.out.println("1. List the first n records based on Type");
        System.out.println("2. List the first n records based listed-in ");

        System.out.println("3. List the first n records based on country and type");
        System.out.println("4. List the first n records in the given range (between start and end date) based on country and type");
        int num=scn.nextInt();

        switch (num) {
            case 1 -> {
                System.out.println("Enter no.of record which you want to view");
                int x = scn.nextInt();
                System.out.println("Enter Type of data");
                String type = input.nextLine();
//                System.out.println(type);
                readData();
//                System.out.println(netflixContent.get(0).size());
                netflixContent.stream()
                        .filter(s -> s.get(1).equals(type))
                        .limit(x)
                        .forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Enter no.of record which you want to view");
                int y = scn.nextInt();
                System.out.println("Enter listed word of data");
                String word = scn.next();
                readData();
                netflixContent.stream()
                        .filter(s -> s.get(10).contains(word))
                        .limit(y)
                        .forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Enter no.of record which you want to view");
                int z = scn.nextInt();
                System.out.println("Enter Type of data");
                String type1 = input.nextLine();
                System.out.println("Enter country of data");
                String country = input.nextLine();
                readData();
                netflixContent.stream()
                        .filter(s -> s.get(1).equals(type1))
                        .filter(s -> s.get(5).equals(country))
                        .limit(z)
                        .forEach(System.out::println);
            }
            case 4 -> {
                System.out.println("Enter no.of record which you want to view");
                int n = scn.nextInt();
                System.out.println("Enter start date");
                String startDate = input.nextLine().trim();
                System.out.println("Enter end date");
                String endDate = input.nextLine().trim();
                printDataInTheRange(startDate, endDate, n);
            }
            default -> System.out.println("Please enter the correct option");


            // code block
        }
    }

    public static void printDataInTheRange(String startDate, String endDate, int n) throws Exception {
        Scanner scn=new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        DateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate start= dateFormat.parse(startDate).toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = dateFormat.parse(endDate).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        readData();
        System.out.println("Enter when which based you want to filter content");
        System.out.println("1. Based on Type");
        System.out.println("2. Based on Listed-in");
        System.out.println("3. Based on type and country");
        int option=scn.nextInt();
        switch (option) {
            case 1 -> {
                System.out.println("Enter Type of data");
                String type = input.nextLine();
                readData();
                netflixContent.stream()
                        .filter(s -> s.get(1).equals(type))
                        .filter(movieRow ->
                        {
                            try {
                                if (movieRow.get(colHeading.get("date_added")).trim().equals("")) {
                                    return false;
                                }
                                LocalDate dateAdded = dateFormat.parse(movieRow.get(colHeading.get("date_added")).replaceAll("\"", "").trim())
                                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return dateAdded.isAfter(start) && dateAdded.isBefore(end);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .limit(n)
                        .forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Enter Type of data");
                String word1 = input.nextLine();
                readData();
                netflixContent.stream()
                        .filter(s -> s.get(10).contains(word1))
                        .filter(movieRow ->
                        {
                            try {
                                if (movieRow.get(colHeading.get("date_added")).trim().equals("")) {
                                    return false;
                                }
                                LocalDate dateAdded = dateFormat.parse(movieRow.get(colHeading.get("date_added")).replaceAll("\"", "").trim())
                                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return dateAdded.isAfter(start) && dateAdded.isBefore(end);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .limit(n)
                        .forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Enter Type of data");
                String type1 = input.nextLine();
                System.out.println("Enter country of data");
                String country = input.nextLine();
                readData();
                netflixContent.stream()
                        .filter(s -> s.get(1).equals(type1))
                        .filter(s -> s.get(5).equals(country))
                        .filter(movieRow ->
                        {
                            try {
                                if (movieRow.get(colHeading.get("date_added")).trim().equals("")) {
                                    return false;
                                }
                                LocalDate dateAdded = dateFormat.parse(movieRow.get(colHeading.get("date_added")).replaceAll("\"", "").trim())
                                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return dateAdded.isAfter(start) && dateAdded.isBefore(end);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return false;
                        })
                        .limit(n)
                        .forEach(System.out::println);
            }
            default -> System.out.println(" Enter the right option");
        }


    }
    public static void readData() throws Exception{

        String pathOfFile= "src/main/resources/netflix_titles.csv";
        BufferedReader csvReader= new BufferedReader(new FileReader(pathOfFile));

        String row=null;
        int numberOfRow=0;

        while((row=csvReader.readLine())!=null){
            List<String> content= Arrays.asList(row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));
            netflixContent.add(content);
            if(numberOfRow==0){
                for(int ind=0;ind<content.size();ind++){
                    colHeading.put(content.get(ind),ind);
                }

            }
            numberOfRow++;
        }
        csvReader.close();


    }

}
