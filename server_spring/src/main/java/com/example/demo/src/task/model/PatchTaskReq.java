package com.example.demo.src.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchTaskReq {
    private boolean complete;
    private String taskName;
}
