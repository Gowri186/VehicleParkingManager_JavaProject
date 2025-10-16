#  Vehicle Parking Manager System

The **Vehicle Parking Manager** is a fully functional desktop application developed in Java using **NetBeans IDE**. It provides a robust Graphical User Interface (GUI) for the efficient, real-time management of parking slots, automated fee calculation, and persistent data storage using a **MySQL** database.

##  Key Features

### Frontend (GUI: `VehicleParkingGUI_NetBeans`)
* **Real-time Slot Management:** Implements a queue (`java.util.Queue`) to track and assign the **lowest available slot number** from a fixed capacity of **50 slots**.
* **Automatic Fee Calculation:** Calculates parking fees based on vehicle type and the total duration, with time rounded up to the nearest full hour.
* **Intuitive UX:** Features **auto-suggestion** on the vehicle number input field, drawing from currently parked vehicles to speed up the exit process.
* **Data Display:** Two tables provide real-time information:
    * **CURRENT:** Vehicles currently parked, slot number, type, and full entry timestamp.
    * **HISTORY:** Records all departed vehicles, including entry/exit times, duration, and final fee.
* **Status Bar:** Provides immediate user feedback on operations, errors, and available slot count.

### Backend (DAO: `ParkingDAO`)
* **Transactional Data Operations:** Manages all database interactions (CRUD). The `exitVehicle` function uses **SQL Transactions** to guarantee that a record is successfully moved to history and removed from current, ensuring data consistency.
* **Database Slot Integrity:** The DAO includes logic (`getNextAvailableSlot`) to query the database and find the first numerical gap in occupied slots, supporting the GUI's queue-based management.
* **Time Handling:** Utilizes `java.time.LocalDateTime` to store and retrieve accurate full date and time timestamps, critical for precise fee calculation.

##  Technology Stack

| Component | Technology / Language | Role |
| :--- | :--- | :--- |
| **GUI** | Java Swing (NetBeans Generated) | User interface and event handling. |
| **Backend** | Java, `Queue`, `LocalDateTime` | Core logic for slot assignment and fee calculation. |
| **Database Access** | Java JDBC, `parking.backend.ParkingDAO` | Abstraction layer for all SQL execution and data flow. |
| **Database** | MySQL Server | Persistent data storage. |

### OUTPUT
https://github.com/user-attachments/assets/f318f296-e0d0-4e85-a332-2c1bfebc236a

