package com.ftn.elastic.ElasticSearch2021.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rules")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private String value;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_condition", nullable = false)
    private ECondition condition;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_operation", nullable = false)
    private EOperation operation;

    @ManyToOne
    @JoinColumn(name = "folder_id", referencedColumnName = "folder_id", nullable = false)
    private Folder folder;
}
