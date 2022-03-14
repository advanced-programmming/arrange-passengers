package com.co.pa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     *
     * Posibles pruebas:
     *
     * 1. shouldDoOkWithListOk
     * 2. shouldDoOkWithEmptyListOk
     * 3. shouldDoOkWithNullListOk
     * 4. shouldDoOkWithListWith1GroupOk
     * 5. shouldDoOkWithListWithoutGroupsOk
     * 6. shouldDoOkWithListWith2GroupsOk
     * 7. shouldDoOkWithListWith3GroupsOk
     * 8. shouldDoOkWithListWithMixedGroupsOk
     * 9. shouldDoOkWithListWithoutGroupsAndSameLoyaltyOk
     * 10. shouldDoOkWithListWithoutGroupsAndDifferentLoyaltyOk
     */

    public static void main(String[] args) {
	    List<Passenger> passengerList = getMockData();
        getCorrectBoardingOrder(passengerList).forEach(passenger ->
                System.out.println("name: "+ passenger.getName() + " loyalty: " + passenger.getLoyaltyClass()));
    }

    public static List<Passenger> getCorrectBoardingOrder(List<Passenger> passengerList){
        List<Passenger> result = updateLoyaltyPerGroups(passengerList);
        result.sort(Comparator.comparing(passenger -> passenger.getLoyaltyClass()));
        return result;
    }

    public static List<Passenger> updateLoyaltyPerGroups(List<Passenger> passengerList){
        List<Passenger> result = new ArrayList<>();
        Map<String, Integer> minValueByGroup = new HashMap<>();
        Map<String, List<Passenger>> mapWithGroups = new HashMap<>();

        if(passengerList != null && !passengerList.isEmpty()) {
            passengerList.forEach(passenger -> {
                String br = passenger.getBookingReference();

                if(mapWithGroups.containsKey(br)) {
                    mapWithGroups.get(br).add(passenger);

                    int min = minValueByGroup.get(br) > passenger.getLoyaltyClass() ?
                            passenger.getLoyaltyClass() : minValueByGroup.get(br);

                    minValueByGroup.put(br, min);
                } else {
                    mapWithGroups.put(br, new ArrayList<>());
                    mapWithGroups.get(br).add(passenger);
                    minValueByGroup.put(br, passenger.getLoyaltyClass());
                }
            });

            mapWithGroups.forEach((k, v) -> {
                v.forEach(passenger -> {
                    passenger.setLoyaltyClass(minValueByGroup.get(k));
                    result.add(passenger);
                });
            });
        }
        return result;
    }

    public static List<Passenger> getMockData(){
        Passenger p1 = new Passenger("Luis", "P1", 1);
        Passenger p3 = new Passenger("Nico", "P1", 3);
        Passenger p4 = new Passenger("Juan", "P1", 4);
        Passenger p2 = new Passenger("Dani", "P2", 2);
        Passenger p5 = new Passenger("John", "P2", 1);
        Passenger p6 = new Passenger("Carlos", "P3", 4);
        Passenger p7 = new Passenger("Felipa", "P3", 6);
        Passenger p8 = new Passenger("Felix", "P3", 10);
        Passenger p9 = new Passenger("David", "P4", 10);
        Passenger p10 = new Passenger("Sebastian", "P5", 2);
        return Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

}

class Passenger {
    private String name;
    private String bookingReference;
    private int loyaltyClass;

    public Passenger(String name, String bookingReference, int loyaltyClass) {
        this.name = name;
        this.bookingReference = bookingReference;
        this.loyaltyClass = loyaltyClass;
    }

    public int getLoyaltyClass() {
        return loyaltyClass;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public String getName() {
        return name;
    }

    public void setLoyaltyClass(int loyaltyClass) {
        this.loyaltyClass = loyaltyClass;
    }
}
