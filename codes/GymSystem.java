package codes;

import java.util.*;
import java.time.LocalDate;

// Main driver class
public class GymSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Member> members = new ArrayList<>();
        List<Plan> plans = new ArrayList<>();
        List<Invoice> invoices = new ArrayList<>();

        int memberId = 1, planId = 1, subId = 1, invoiceId = 1, recordId = 1;

        while (true) {
            System.out.println("\n--- Gym Membership & Billing System ---");
            System.out.println("1. Add Member");
            System.out.println("2. Add Plan");
            System.out.println("3. Start or Renew Subscription");
            System.out.println("4. Record Attendance");
            System.out.println("5. Generate Invoice");
            System.out.println("6. Record Payment");
            System.out.println("7. Display Members");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();
                    members.add(new Member(memberId++, name, phone));
                    break;
                case 2:
                    System.out.print("Plan Name: ");
                    String pname = sc.nextLine();
                    System.out.print("Duration (months): ");
                    int dur = sc.nextInt();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Penalty Rate: ");
                    double pen = sc.nextDouble();
                    plans.add(new Plan(planId++, pname, dur, price, pen));
                    break;
                case 3:
                    System.out.print("Member ID: ");
                    int mid = sc.nextInt();
                    System.out.print("Plan ID: ");
                    int pid = sc.nextInt();
                    Member m = members.get(mid - 1);
                    Plan p = plans.get(pid - 1);
                    Subscription s = new Subscription(subId++, m, p);
                    s.activate();
                    m.setSubscription(s);
                    System.out.println("Subscription activated: " + s);
                    break;
                case 4:
                    System.out.print("Member ID: ");
                    int mId = sc.nextInt();
                    Member m1 = members.get(mId - 1);
                    m1.addAttendance(new AttendanceRecord(recordId++, m1, LocalDate.now()));
                    break;
                case 5:
                    System.out.print("Member ID: ");
                    int mm = sc.nextInt();
                    Member m2 = members.get(mm - 1);
                    Invoice inv = new Invoice(invoiceId++, m2.getSubscription(), 0.18);
                    invoices.add(inv);
                    System.out.println(inv);
                    break;
                case 6:
                    System.out.print("Invoice ID: ");
                    int invid = sc.nextInt();
                    Invoice inv1 = invoices.get(invid - 1);
                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();
                    Payment pay = new Payment(1, inv1, amt);
                    pay.processPayment();
                    break;
                case 7:
                    for (Member mem : members) {
                        System.out.println(mem);
                        mem.showAttendance();
                    }
                    break;
                case 8:
                    System.out.println("Exiting system...");
                    System.exit(0);
            }
        }
    }
}

// ---------------- Supporting Classes ----------------

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
        return "Record " + recordId + ": attended on " + date;
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

    public double getTotal() { return total; }

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
        if (!invoice.isPaid() && amount >= invoice.getTotal()) {
            invoice.markPaid();
            System.out.println("Payment successful for Invoice: " + invoice);
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