package com.demo.wms.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by anton_kramarev on 8/12/2016.
 */

@Entity
@Table(name = "orders")
public class Order {

    public enum Status {
        NEW, COMPLETED, CANCELED
    }

    @Id
    @GeneratedValue
    private Long id;
    private Status status;
    private Date submittedDate;
    @OneToOne
    private User manager;
    @OneToMany
    private List<OrderLine> lines = new LinkedList<>();

    public Order() {
        this.status = Status.NEW;
    }


    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public void removeLine(OrderLine line) {
        lines.remove(line);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
