# 🍔 RuDash: Fast Food Ordering App 

Your one-stop Android app for delicious fast food, meticulously crafted and feature-rich! This project is an Android evolution of a JavaFX desktop application.

## 🚀 Project Genesis & Vision

RuDash brings a familiar JavaFX desktop food ordering experience to the convenience of your Android device. It's designed to showcase robust Android development practices, including:

*   📱 **Intuitive Multi-Activity Navigation:** Seamlessly move between different sections of the app.
*   🧩 **Singleton Pattern for Global Data:** Efficiently manages app-wide data like `StoredOrderSingleton.java`.
*   📜 **Dynamic RecyclerViews:** Smoothly displays lists with custom adapters, like for beverage flavors.
*   🎨 **Tailored Custom Layouts:** Unique and user-friendly interfaces for each food category.
*   🛡️ **Comprehensive Exception Handling:** Ensuring a stable and crash-free user experience.

## 🌟 Core Features: What Can You Do?

Order your favorites just the way you like them!

*   🥪 **Menu Customization:** Personalize sandwiches, burgers, beverages, and sides to your exact taste.
*   🥤 **Combo Creation:** Bundle items into combo meals for great value.
*   🛒 **Order Management:** Easily view, modify, and place your food orders.
*   📜 **Order History:** Look up your previous orders and cancel them if necessary.

## 🛠️ Tech Stack & Architecture

RuDash is built with a solid and scalable foundation:

*   🏛️ **Architectural Pattern:** Model-View-Controller (MVC) for a clear separation of concerns.
*   💾 **Data Management:** Leverages the Singleton Pattern for consistent data sharing across activities.
*   📱 **Android UI Toolkit:** Makes extensive use of core Android components:
    *   Activities
    *   RecyclerView
    *   ListView
    *   Toast notifications
    *   AlertDialogs

## 🧩 Key Components & UI Highlights

Dive into the building blocks of RuDash:

### Screens (Activities) 📱
*   `MainActivity.java`: The main navigation hub – your gateway to deliciousness!
*   `SandwichActivity.java`: Customize and order your perfect sandwich.
*   `BurgerActivity.java`: Build your dream burger with all the fixings.
*   `BeverageActivity.java`: Select refreshing beverages, featuring a dynamic RecyclerView for flavors.
*   `SideActivity.java`: Choose and customize your favorite side items.
*   `ComboActivity.java`: Create your own combo meals.
*   `OrderActivity.java`: Review and manage your current order before placing it.
*   `StoreOrdersActivity.java`: Access and manage your history of placed orders.

### Data Adapters 🔄
*   `FlavorAdapter.java`: A custom `RecyclerView.Adapter` that beautifully displays beverage flavors, each with a unique image.

### Data Blueprints (Models & Enums) 🧱
*   **Core Models:**
    *   `MenuItem.java`: Abstract base class for all menu offerings.
    *   `Sandwich.java`, `Burger.java`, `Beverage.java`, `Side.java`: Specific classes for each food type.
    *   `Combo.java`: Represents a combined meal deal.
    *   `Order.java`: Holds details of the current order.
    *   `StoredOrder.java`: Manages collections of multiple orders (order history).
*   **Supporting Enums:** `Bread`, `Protein`, `AddOns`, `Flavor`, `Size`, `SideOption` provide predefined choices for customization.

### ✨ UI Magic
*   Visually appealing `RecyclerView` for beverage flavors, each with its own distinct image.
*   Custom drawable resources (e.g., `cola.xml`, `diet_cola.xml`, `default_drink.xml`) for a polished look and feel.
*   Thoughtfully designed custom layouts for each food category, enhancing user experience.

## 🚀 Get It Running: Setup Guide

Ready to explore RuDash? Here’s how:

### Prerequisites ✅
*   Android Studio (latest stable version recommended)
*   Minimum SDK: API 34
*   Target Device: Optimized for Pixel 3a XL (or a compatible Android emulator/device)

### Installation Steps 🛠️
1.  **Clone the Repository:**
    ```sh
    git clone https://github.com/your-username/rudash.git 
    # Replace with your actual repository URL
    ```
2.  **Open in Android Studio:** Launch Android Studio and import the cloned project.
3.  **Build & Run:** Let Android Studio sync and build the project. Run it on a Pixel 3a XL API 36 emulator or a physical device.

## 📂 Project Demo
