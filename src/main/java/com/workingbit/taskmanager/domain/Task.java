package com.workingbit.taskmanager.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Aleksey Popryaduhin on 09:39 15/07/2017.
 */
@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String email;

    private String content;

    private boolean completed;

    @ElementCollection
    @Column(columnDefinition="TEXT")
    private List<String> pictures;
}
