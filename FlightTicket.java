//  Flight Ticket Booking

/*
 * book
 * cancel
 * flight details
 * exit
 * 
 * 
 * book : 
 * flight 50 seats
 * flight name,seats,passenger name
 * ticket : 5000,n*200
 * 
 * cancel :
 * refund
 * --200
 * 
 * class :
 * 
 * Main
 * Flight
 * Passenger
 * Booker
 */

 import java.util.*;

import javax.swing.plaf.synth.SynthStyle;

class FlightTicket
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        Booker booker = new Booker();

        Passenger passenger;
        String name;
        int flight_id,tickets_booked,passenger_id;
        List<Passenger> passengers;

        while(true)
        {
            System.out.println("Choose your option : \n");
            System.out.printf("1.Book Ticket.\n2.Cancel Ticket.\n3.Show Flight Details.\n4.Exit\n");
            switch(scan.nextInt())
            {
                case 1:
                System.out.println("Enter your name : ");
                while(!scan.nextLine().isEmpty());
                name = scan.nextLine();

                System.out.println("Enter the Flight ID : ");
                flight_id = scan.nextInt();

                System.out.println("Enter the required no. of tickets : ");
                tickets_booked  = scan.nextInt();

                passenger = new Passenger(name, flight_id, tickets_booked);
                booker.add(passenger);
                break;

                case 2:
                System.out.println("Enter your Passenger ID : ");
                passenger_id  = scan.nextInt();
                System.out.println("Enter your Flight ID : ");
                flight_id  = scan.nextInt();
                booker.cancel(passenger_id, flight_id);
                break;

                case 3:
                System.out.println("......Flight Deteails.....\n");
            
                for(int i=0;i<Booker.FLIGHT_SIZE;++i)
                {
                    passengers = booker.flight_details.get(i+1);
                    System.out.printf("\n\nFlight Name : %s      Flight ID : %d\n\n",Booker.FLIGHT_NAMES[i],i+1);
                    
                    for(Passenger pass:passengers)
                    {
                        System.out.printf("Passenger Name : %s\n",pass.name);
                        System.out.printf("Passenger ID : %d\n",pass.id);
                        System.out.printf("Passenger Cost : %d\n",pass.cost);
                        System.out.printf("Tickets Booked : %d\n\n",pass.tickets_booked);
                    }
                }
                break;

                case 4:
                scan.close();
                System.exit(0);
                break;
            }
        }
    }
}

class Flight 
{
    String name;
    int id;

    Flight(String name,int id)
    {
        this.name = name;
        this.id = id;
    }
}

class Passenger
{
    static int CURRENT_ID = 1;
    int cost = 5000;

    String name;
    int id,tickets_booked,flight_id;

    Passenger(String name,int flight_id,int tickets_booked)
    {
        this.name = name;
        this.id = CURRENT_ID++;
        this.flight_id = flight_id;
        this.tickets_booked = tickets_booked;
        cost+=200*tickets_booked;
    }
}

class Booker
{
    static int FLIGHT_SIZE = 3;
    static String[] FLIGHT_NAMES = {"MAS TO TRT","MAS TO AKM","AKM TO TRT"};
    static int MAX_TICKETS_PER_FLIGHT = 3;

    Flight[] flights = new Flight[FLIGHT_SIZE];
    Map<Integer,List<Passenger>> flight_details;

    Booker()
    {
        flight_details = new HashMap<>();
        for(int i=0;i<FLIGHT_SIZE;++i)
        {
            flights[i] = new Flight(FLIGHT_NAMES[i],i+1);
            flight_details.put(flights[i].id,new ArrayList<>());
        }
    }

    void add(Passenger passenger)
    {
        List<Passenger> list = flight_details.get(passenger.flight_id);
        
        if(list==null)
        {
            System.out.println("Invalid Flight ID");
            return;
        }
        if(list.size()+passenger.tickets_booked<=MAX_TICKETS_PER_FLIGHT)
        {
            list.add(passenger);
            flight_details.put(passenger.id, list);
            System.out.printf("...Ticket Booked...\nTotal Cost : %d\n\n",passenger.cost);
        }
    }

    void cancel(int passenger_id,int flight_id)
    {
        List<Passenger> list = flight_details.get(flight_id);
        if(list==null)
        {
            System.out.println("Invalid flight ID\n");
            return;
        }
        list.remove(passenger_id);
        flight_details.put(passenger_id, list);
    }
}