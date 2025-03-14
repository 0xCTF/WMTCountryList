# 🌍 WMTCountryList

WMTCountryList is an Android application that fetches and displays country data using **MVVM with Clean architecture, Hilt for dependency injection, Retrofit for API calls, and Coroutines for async processing**.  

## ✨ Features
- Fetches country data from an API.
- Displays a list of countries with their name, region, and capital.
- Uses **StateFlow** to manage UI state.
- Handles **Loading, Success, Network Error, and Server Error** states.

## 📷 UI States
Below are the different states of the application:

### 🔄 **Loading State**
- Indicates that the data is being fetched.
- A progress bar is shown to inform the user.

<img src="./images/loading.png" alt="Loading State" width="40%">


---

### ✅ **Success State**
- The data is successfully fetched and displayed in the UI.

<img src="./images/app%20ui.png" alt="App UI" width="40%">

---

### 🌐 **Network Error**
- Occurs when the device has no internet connection.
- Displays an error message prompting the user to check their connection.

<img src="./images/network%20error.png" alt="Network Error" width="40%">

---

### 🔥 **Server Error**
- Happens when the API returns an error response.
- Displays an appropriate message to notify the user.

<img src="./images/server%20error.png" alt="Server Error" width="40%">

