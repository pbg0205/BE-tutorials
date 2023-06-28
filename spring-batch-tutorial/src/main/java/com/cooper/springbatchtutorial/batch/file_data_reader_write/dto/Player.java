package com.cooper.springbatchtutorial.batch.file_data_reader_write.dto;

import lombok.Data;

@Data
public class Player{

    private String ID;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

}
