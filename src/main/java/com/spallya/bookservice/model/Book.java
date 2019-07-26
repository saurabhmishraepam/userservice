package com.spallya.bookservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Book Model
 *
 * @author Spallya Omar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated book ID")
    private Long id;

    @ApiModelProperty(notes = "The name of the book", required = true)
    private String name;

    @ApiModelProperty(notes = "The author of the book", required = true)
    private String author;

    @ApiModelProperty(notes = "The genre of the book")
    private String genre;

    @ApiModelProperty(notes = "The description of the book")
    private String description;

    @ApiModelProperty(notes = "The price of the book", required = true)
    private Double price;

    @ApiModelProperty(notes = "The published year of the book", required = true)
    private String publishedYear;

}
