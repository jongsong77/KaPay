package com.kakaopay.sc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCondition {
    private String sk;  //search key
    private String sv;  //search value
    private String sk2;  //search key2
    private String sv2;  //search value2
}