import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RatingQuery {
    private ArrayList<Rating> edges;
    private ArrayList<Product> productList;
    private ArrayList<Customer> customerList ;

    public RatingQuery(String customerPath, String productPath, String ratingPath){
        edges = new ArrayList<Rating>();
        productList = new ArrayList<Product>();
        customerList = new ArrayList<Customer>();
        readCustomerFile(customerPath);
        readProductFile(productPath);
        buildGraph(ratingPath);
    }

    public void printGraph(){
		for(int i = 0; i < edges.size(); i++){
			System.out.println(edges.get(i));
		}
	}

    public boolean readCustomerFile(String filePath){
        try{
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                String content = sc.nextLine();
                String[] attributes = content.split(",");
                Customer tmpCustomer = new Customer(attributes[0], attributes[1], attributes[2]);
                customerList.add(tmpCustomer);
            }
            sc.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return false;
        } 
        return true;
    }

    public boolean readProductFile(String filePath){
        try{
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                String content = sc.nextLine();
                String[] attributes = content.split(",");
                Product tmpProduct = new Product(Integer.parseInt(attributes[0]), attributes[1], attributes[2]);
                productList.add(tmpProduct);
            }
            sc.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return false;
        } 
        return true;
    }

    public void addEdge(Customer u, Product v, int w){
        edges.add(new Rating(u,v,w));
	}

    public boolean buildGraph(String filePath){
        try{
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                String content = sc.nextLine();
                String[] attributes = content.split(",");
                Customer tmpCustomer = new Customer();
                Product tmpProduct = new Product();
                for(Customer c : customerList){
                    if(c.getCustomerId().equals(attributes[0]))
                        tmpCustomer = c;

                }
                for(Product p : productList){
                    if(p.getProductId() == Integer.parseInt(attributes[1]))
                        tmpProduct = p;
                }
                addEdge(tmpCustomer, tmpProduct, Integer.parseInt(attributes[2]));
            }
            sc.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found");
            return false;
        } 
        return true;
    }

    public ArrayList<Product> query1(String customerId){
        ArrayList<Product> list= new ArrayList<>();
        for (Rating item:edges){
            if (item.getCustomer().getCustomerId().equals(customerId)){
                if (item.getW() >= 3){
                    list.add(item.getProduct());
                }
            }
        }
        return list;
    }

    public Integer query2(int productId){
        int count = 0;
        for (Rating item:edges){
            if (item.getCustomer().getGender().equals("M")){
                if (item.getProduct().getProductId() == productId){
                    if (item.getW() >= 3){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public ArrayList<Product> query3(){
        ArrayList<Product> list = new ArrayList<>();
        int M=0;
        int F=0;
        for (Product product:productList){
            for (Rating item:edges){

                if (item.getProduct().getProductId()==product.getProductId()){
                    if (item.getCustomer().getGender().equals("F"))
                        F++;
                    if(item.getCustomer().getGender().equals("M"))
                        M++;
                }
            }
                if (F>=M)
                    list.add(product);
            M=0;
            F=0;
        }
        return list;
    }

    public <E> boolean writeFile(String filePath, ArrayList<E> list){
        try{
            File f = new File(filePath);
            FileWriter fw = new FileWriter(f);
            if(list.size() == 0){
                fw.write("No item here");
                fw.close();
                return true;
            }
            for(E data : list){
                fw.write(data + "\n");
            }
            fw.close();
        }catch(IOException e){
            System.out.println("Can't write file");
            return false;
        }

        return true;
    }

    public <E> boolean writeFile(String filePath, E data){
        try{
            File f = new File(filePath);
            FileWriter fw = new FileWriter(f);
            fw.write(data.toString());
            fw.close();
        }catch(IOException e){
            System.out.println("Can't write file");
            return false;
        }
        return true;    
    }
}
