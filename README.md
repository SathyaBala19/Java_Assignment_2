# 📘 Gym Membership & Billing System

## 📝 Problem Statement

Design and implement a **Java console application** for a Gym Membership & Billing System that manages **memberships, plans, attendance, payments, and renewals**.
The system must demonstrate **object-oriented principles** and maintain accurate **membership status and dues**.

---

## ⚙️ Features Implemented

* Add Member & Add Plan
* Start or Renew Subscription
* Record Attendance (only if subscription is active)
* Generate Invoice (includes subtotal, taxes, penalties, total, payment status)
* Record Payment (invoices must be generated before accepting payment)
* Display Members with their subscriptions and attendance
* Exit system

**Business Rules:**

1. A member must have an active subscription to record attendance.
2. Subscription activation/renewal updates validity dates immediately.
3. Invoices must be generated before accepting payments.
4. Late renewals may include a penalty fee as per plan policy.
5. Attendance records should link directly to members and dates.

---

## 📂 Project Structure

```
GymSystem.java   <-- Main program (includes all classes)
README.md        <-- This file
```

---

## ▶️ How to Compile & Run

### 1. Compile

```bash
javac GymSystem.java
```

### 2. Run

```bash
java GymSystem
```

---

## 👨‍🎓 Student Details

* **Name:** B Sathya Bala
* **Roll No:** 717824I252
