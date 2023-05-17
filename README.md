# PlantShop

An e-commerce application where you can buy plants

To build the foundation of the application, I utilized Android Jetpack components and followed the MVVM (Model-View-ViewModel) architecture to ensure clean and modular code. The MVVM architecture separates the concerns of the application into three distinct layers: the Model, View, and ViewModel.

Using the MVVM pattern, I created the Model layer to represent the data and business logic of the application. Firebase Auth was implemented for registration and login processes, and Firestore was used to store users' profile photos.

To enhance the code readability and reduce boilerplate, I leveraged data binding. Data binding allowed me to establish a direct connection between the UI components and the underlying data, enabling seamless updates and reducing the need for manual UI updates.

For data retrieval from the internet, I created a simple API using GitHub Gist and utilized the Retrofit library to handle network requests efficiently.

To ensure efficient updates and better performance, I replaced the conventional RecyclerView with ListAdapter, which leverages the DiffCallback method to automatically update the data.

To provide real-time updates and maintain UI consistency, I incorporated LiveData, a lifecycle-aware data holder, into the ViewModel layer. LiveData enabled me to observe changes in data and propagate them to the UI seamlessly.

The functionality of adding products to the cart or favorites was implemented using SQLite Room database. Room allowed me to create a local database with clean and efficient queries, ensuring smooth user experience and data synchronization.

In terms of dependency management, I utilized the Hilt Dagger library. Hilt simplifies dependency injection, enabling better management of dependencies between different components of the application and reducing code duplication.

By incorporating the MVVM architecture, data binding, and other clean coding practices, I aimed to create a well-structured, maintainable, and efficient application. This approach enhances readability, testability, and scalability, allowing for easier collaboration and future enhancements.
