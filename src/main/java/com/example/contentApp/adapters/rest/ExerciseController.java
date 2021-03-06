package com.example.contentApp.adapters.rest;

import com.example.contentApp.ports.incoming.CreateContentUseCase;
import com.example.contentApp.ports.incoming.FindContentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {


    @GetMapping(path = "two_sum")
    public ResponseEntity<Object> getTwoSum(@RequestParam(value = "array") int[] array, @RequestParam(value = "desiredsum") Integer desiredSum){
        int[] arr = array;

        for (int x =0; x < arr.length; x++){
            for (int j =x +1; j < arr.length; j++){
                if(arr[x] + arr[j] == desiredSum)
                    return ResponseEntity.ok(new int[] {x,j});
            }
        }

        return ResponseEntity.ok("null");
    }

    @GetMapping(path = "balance")
    public ResponseEntity<Object> balance(@RequestParam(value = "values") int[] A, @RequestParam(value = "dates") String[] D){
        int totalValue = 0;
        int totalFeeInYear = 60;
        int cardPayments =0;
        int valueInMonth = 0;
        HashMap<Integer,Integer> totalValuesTable = new HashMap<Integer, Integer>();
        HashMap<Integer,Integer> valuesTable = new HashMap<Integer, Integer>();

        for(int x = 0; x< A.length; x++){
            LocalDate date = LocalDate.parse(D[x]);
            totalValue += A[x];

            if(A[x] < 0)  {
                if(valuesTable.get(date.getMonthValue()) == null){
                    totalValuesTable.put(date.getMonthValue(), 0);
                    valuesTable.put(date.getMonthValue(),0);
                }
                cardPayments = valuesTable.get(date.getMonthValue());
                valueInMonth = totalValuesTable.get(date.getMonthValue()) + A[x];

                totalValuesTable.put(date.getMonthValue(),valueInMonth);
                valuesTable.put(date.getMonthValue(),++cardPayments );
                System.out.println("month: " +date.getMonthValue() + " acumulated value: " +  valueInMonth + " number of payments: " + cardPayments);
            }

        }
        System.out.println("===summary===");
        for (Map.Entry<Integer,Integer> pair : valuesTable.entrySet()) {
            System.out.println("month: " + pair.getKey() + " transactions count: " +  pair.getValue() +" amount:" + totalValuesTable.get(pair.getKey()));
            if(pair.getValue() >=3){
                if(totalValuesTable.get(pair.getKey()) <= -100)
                    totalFeeInYear = totalFeeInYear -5;
            }
        }
        System.out.println("Fee in charge: "+ totalFeeInYear);

        totalValue = totalValue - totalFeeInYear;

        System.out.println("balance:" + totalValue);

        return ResponseEntity.ok(totalValue);
    }

    /*
        Write a Java program to divide a string in n equal parts.
        The given string is: abcdefghijklmnopqrstuvwxy
        The string divided into 5 parts and they are:

        abcde
        fghij
        klmno
        pqrst
        uvwxy

        String str = "abcdefghijklmnopqrstuvwxy"; //length 25
        int split_number = 5;
        splitString(str, split_number);

        str = "abcfdefghijkl"; //length 13
        split_number = 3;
        splitString(str, split_number);

        str = "abcfdefghi"; //length 10
        split_number = 2;
        splitString(str, split_number);
     */
    @GetMapping(path = "split_string")
    public ResponseEntity<Object> splitString(@RequestParam(value = "str") String str, @RequestParam(value = "split_number") Integer splitNumber){
        if(str.length() % splitNumber != 0){
            return ResponseEntity.badRequest().body("It can`t be divided into equal parts.");
        }

        int begin = 0;
        int end = str.length() / splitNumber;
        ArrayList<String> results = new ArrayList<>();

        for (int i = 0; i < splitNumber; i++) {
            results.add(str.substring(begin, end ));
            begin = begin + splitNumber;
            end = end + splitNumber;
        }

        return ResponseEntity.ok(results);
    }
}
