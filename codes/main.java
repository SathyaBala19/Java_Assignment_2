package codes;
import java.time.LocalDate;
import java.util.*;

public class main {
    class Member {
        private int memberId;
        private String name;
        private String phone;
        private Subscription subscription;
        private List<AttendanceRecord> attendanceList;

        public Member(int memberId, String name, String phone) {
            this.memberId = memberId;
            this.name = name;
            this.phone = phone;
            this.attendanceList = new ArrayList<>();
        }

        public void setSubscription(Subscription subscription) {
            this.subscription = subscription;
        }

        public Subscription getSubscription() {
            return subscription;
        }

        public void addAttendance(AttendanceRecord record) {
            if (subscription != null && subscription.isActive()) {
                attendanceList.add(record);
            } else {
                System.out.println("Cannot record attendance. Subscription inactive.");
            }
        }

        public void showAttendance() {
            System.out.println("Attendance for " + name + ":");
            for (AttendanceRecord a : attendanceList) {
                System.out.println(" - " + a);
            }
        }

        public String toString() {
            return memberId + " | " + name + " | " + phone +
                    (subscription != null ? " | Subscription Active: " + subscription.isActive() : " | No subscription");
        }
    }

    class Plan {
        private int planId;
        private String name;
        private int duration; // months
        private double price;
        private double penaltyRate;

        public Plan(int planId, String name, int duration, double price, double penaltyRate) {
            this.planId = planId;
            this.name = name;
            this.duration = duration;
            this.price = price;
            this.penaltyRate = penaltyRate;
        }

        public int getDuration() { return duration; }
        public double getPrice() { return price; }
        public double getPenaltyRate() { return penaltyRate; }

        public String toString() {
            return planId + " | " + name + " | " + duration + " months | $" + price;
        }
    }

    class Subscription {
        private int subscriptionId;
        private Member member;
        private Plan plan;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean active;

        public Subscription(int subscriptionId, Member member, Plan plan) {
            this.subscriptionId = subscriptionId;
            this.member = member;
            this.plan = plan;
        }

        public void activate() {
            this.startDate = LocalDate.now();
            this.endDate = startDate.plusMonths(plan.getDuration());
            this.active = true;
        }

        public void renew() {
            this.startDate = LocalDate.now();
            this.endDate = startDate.plusMonths(plan.getDuration());
            this.active = true;
        }

        public boolean isActive() {
            return active && LocalDate.now().isBefore(endDate);
        }

        public Plan getPlan() { return plan; }

        public String toString() {
            return "Subscription [" + subscriptionId + "] Plan: " + plan +
                    " Start: " + startDate + " End: " + endDate + " Active: " + isActive();
        }
    }

    class AttendanceRecord {
        private int recordId;
        private Member member;
        private LocalDate date;

        public AttendanceRecord(int recordId, Member member, LocalDate date) {
            this.recordId = recordId;
            this.member = member;
            this.date = date;
        }

        public String toString() {
            return "Record " + recordId + ": " + member + " attended on " + date;
        }
    }

    class Invoice {
        private int invoiceId;
        private Subscription subscription;
        private double subtotal;
        private double tax;
        private double penalty;
        private double total;
        private boolean paid;

        public Invoice(int invoiceId, Subscription subscription, double taxRate) {
            this.invoiceId = invoiceId;
            this.subscription = subscription;
            this.subtotal = subscription.getPlan().getPrice();
            this.tax = subtotal * taxRate;
            this.penalty = 0.0;
            this.total = subtotal + tax;
            this.paid = false;
        }

        public void addPenalty(double penalty) {
            this.penalty = penalty;
            this.total += penalty;
        }

        public void markPaid() {
            this.paid = true;
        }

        public boolean isPaid() { return paid; }

        public String toString() {
            return "Invoice [" + invoiceId + "] Subtotal: $" + subtotal +
                    " Tax: $" + tax + " Penalty: $" + penalty +
                    " Total: $" + total + " Paid: " + paid;
        }
    }

    class Payment {
        private int paymentId;
        private Invoice invoice;
        private double amount;
        private LocalDate date;

        public Payment(int paymentId, Invoice invoice, double amount) {
            this.paymentId = paymentId;
            this.invoice = invoice;
            this.amount = amount;
            this.date = LocalDate.now();
        }

        public void processPayment() {
            if (!invoice.isPaid() && amount >= invoice.total) {
                invoice.markPaid();
                System.out.println("Payment successful for Invoice " + invoice);
            } else {
                System.out.println("Payment failed. Check amount or invoice status.");
            }
        }
    }

    class Trainer {
        private int trainerId;
        private String name;
        private String specialization;

        public Trainer(int trainerId, String name, String specialization) {
            this.trainerId = trainerId;
            this.name = name;
            this.specialization = specialization;
        }

        public String toString() {
            return trainerId + " | " + name + " | " + specialization;
        }
    }

}