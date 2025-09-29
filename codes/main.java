package codes;

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
                    System.exit(0);
            }
        }
    }
}
