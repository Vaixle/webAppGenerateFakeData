package com.petushkov.webappgeneratedata.controllers;

import com.github.javafaker.Faker;
import com.petushkov.webappgeneratedata.domain.TableDataRow;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/table")
@CrossOrigin
@SessionAttributes(value = {"faker", "random"})
public class TableController {


    @GetMapping()
    public ModelAndView getTable(Model model,
                                 @RequestParam String region,
                                 @RequestParam String seed,
                                 @RequestParam String error,
                                 @RequestParam String fakerParam,
                                 @RequestParam long index){

        double erorrCount = Double.parseDouble(error);
        long seedLong = Long.parseLong(seed);


        if(fakerParam.equals("new")) {
            Random random = new Random(seedLong);
            model.addAttribute("random", random);
            model.addAttribute("faker", new Faker(new Locale(region), random));

        }
        Faker faker = (Faker) model.getAttribute("faker");
        Random random = (Random) model.getAttribute("random");

        List<TableDataRow> table = new ArrayList<>();

        for(Long i = index + 1;  i<= index +20; i++){
            TableDataRow row = new TableDataRow();
            row.setIndex(i);
            row.setIdentifier(String.valueOf(faker.number().randomNumber()));
            row.setFullName(faker.name().fullName());
            row.setAddress(faker.address().fullAddress());
            row.setPhone(faker.phoneNumber().phoneNumber());

            row = addErrors(row, random, erorrCount);

            if(String.valueOf(erorrCount).split("\\.")[1].equals("5")) {
                row = generateRandomIntIntRange(2, random) == 1 ? addErrors(row, random, 1) : row;
            }
            table.add(row);
        }

        ModelAndView mv= new ModelAndView("table::new_rows");
        mv.addObject("table", table);
        return mv;
    }


    public static TableDataRow addErrors(TableDataRow row, Random random, double errorCount) {

        if (errorCount < 0.5)
            return row;


        List<Integer> errorsFields = getFieldsWhereErrorOccurs(errorCount, random);

        String errorField = "";
        int whichField = 0;

        for (int i = 0; i < errorsFields.size(); i++) {
            switch (errorsFields.get(i)) {
                case 0:
                    errorField = row.getIdentifier();
                    whichField = 0;
                    break;
                case 1:
                    errorField = row.getFullName();
                    whichField = 1;
                    break;
                case 2:
                    errorField = row.getAddress();
                    whichField = 2;
                    break;
                case 3:
                    errorField = row.getPhone();
                    whichField = 3;
                    break;
            }

            int whichError = getWhichErrorOccur(random);

            switch (whichError) {
                case 0:
                    errorField = deleteOneSymbol(errorField, random);
                    break;
                case 1:
                    errorField = addOneSymbol(errorField, random);
                    break;
                case 2:
                    errorField = switchPositions(errorField, random);
                    break;

            }
            switch (whichField) {
                case 0:
                    row.setIdentifier(errorField);
                    break;
                case 1:
                    row.setFullName(errorField);
                    break;
                case 2:
                    row.setAddress(errorField);
                    break;
                case 3:
                    row.setPhone(errorField);
                    break;
            }

        }

    return row;

   }




    public static List<Integer> getFieldsWhereErrorOccurs(double errorCount, Random random) {

        List<Integer> fields  = new ArrayList<>();

        for (int i = 0; i < errorCount; i++) {
            fields.add(generateRandomIntIntRange( 4 ,random));
        }

        return fields;
    }


    public static int getWhichErrorOccur(Random random) {

        return generateRandomIntIntRange( 3 ,random);
    }



    public static String deleteOneSymbol(String tableField, Random random) {

        if(tableField.length() == 1)
            return tableField;

        int symbolDeleteNumber = generateRandomIntIntRange(tableField.length(), random);

        return new StringBuffer(tableField).deleteCharAt(symbolDeleteNumber).toString();
    }


    public static String addOneSymbol(String tableField, Random random) {

        if(tableField.isEmpty())
            return "";

        int symbolAddNumber = generateRandomIntIntRange(tableField.length(), random);
         StringBuffer myString = new StringBuffer(tableField);
         char chosenChar = myString.charAt(symbolAddNumber);

        return myString.insert(symbolAddNumber, chosenChar).toString();
    }


    public static String switchPositions(String tableField, Random random) {

        if(tableField.length() == 1)
            return tableField;

        int symbolAddNumber = generateRandomIntIntRange(tableField.length(), random);
        StringBuffer myString = new StringBuffer(tableField);

        char chosenChar = myString.charAt(symbolAddNumber);

        myString.deleteCharAt(symbolAddNumber);

        try {
            myString.insert(symbolAddNumber + 1, chosenChar);
        } catch (StringIndexOutOfBoundsException e) {
            myString.insert(symbolAddNumber - 1, chosenChar);
        }
        return myString.toString();
    }




    public static int generateRandomIntIntRange(int max, Random random) {
        return random.nextInt(max);
    }

}
