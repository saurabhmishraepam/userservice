package com.spallya.bookservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "The name of the book must not be blank!")
    private String name;

    @ApiModelProperty(notes = "The author of the book", required = true)
    @NotBlank(message = "The author of the book must not be blank!")
    private String author;

    @ApiModelProperty(notes = "The genre of the book")
    private String genre;

    @ApiModelProperty(notes = "The description of the book")
    private String description;

    @ApiModelProperty(notes = "The price of the book", required = true)
    @NotNull(message = "The price of the book must not be null!")
    private Double price;

    @ApiModelProperty(notes = "The published year of the book", required = true)
    @NotBlank(message = "The published year of the book must not be blank!")
    private String publishedYear;

}
