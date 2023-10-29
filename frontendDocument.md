# AllHistory Folder 

## Overview
The "AllHistory" folder contains components that are responsible for displaying historical data related to different aspects of your application. These components fetch data from various API endpoints and present it in a structured tabular format.

## Components
### 1. `AllHistory`
- **Purpose:** To display a table showing the history of network devices, manufacturer information, and software.
- **Key Functionalities:**
  - Fetching and displaying historical data of network devices, manufacturers, and software.
  - Managing multiple tabs to switch between different types of historical data.

### 2. `ManufacturerHistory`
- **Purpose:** To display a table containing the history of manufacturers.
- **Key Functionalities:**
  - Fetching and displaying historical data of manufacturers.
  - Rendering data in a tabular format with columns for serial number, manufacturer name, email, website, field of work, and action.

### 3. `SoftwareHistory`
- **Purpose:** To display a table containing the history of software.
- **Key Functionalities:**
  - Fetching and displaying historical data of software.
  - Rendering data in a tabular format with columns for serial number, software name, purchase date, expiry date, type of plan, total licenses, version, price of software, and action.

## Usage
These components are intended for use in your application to provide users with insights into the historical data related to network devices, manufacturers, and software. They can be embedded within your application's UI or views to showcase the relevant historical information.

### Example Usage:
```jsx
import React from "react";
import AllHistory from "./AllHistory/AllHistory";

function App() {
  return (
    <div>
      {/* Other components */}
      <AllHistory />
      {/* Other components */}
    </div>
  );
}

export default App;
```

# Dashboard Component Documentation

## Overview
The `Dashboard` component is a central part of your application, serving as the main dashboard that provides an overview of your software licenses and recent orders. It includes summary cards for different license types and a chart displaying license-related actions. Users can navigate to specific sections of the application by clicking on these cards.

## Usage
The `Dashboard` component should be included in your application's UI to offer users a comprehensive view of license-related data.

### Example Usage:
```jsx
import React from "react";
import Dashboard from "./Dashboard"; // Adjust the import path as needed

function App() {
  return (
    <div>
    {/* Other components */}
    <Dashboard />
    {/* Other components */}
  </div>
  );
}

export default App;
```

# Dashboard Component 

## Overview
The `Dashboard` component is a central part of your application, serving as the main dashboard that provides an overview of your software licenses and recent orders. It includes summary cards for different license types and a chart displaying license-related actions. Users can navigate to specific sections of the application by clicking on these cards.

## Usage
The `Dashboard` component should be included in your application's UI to offer users a comprehensive view of license-related data.

### Example Usage:
```jsx
import React from "react";
import Dashboard from "./Dashboard"; // Adjust the import path as needed

function App() {
  return (
    <div>
    {/* Other components */}
    <Dashboard />
    {/* Other components */}
  </div>
  );
}

export default App;

```

# Key Features

## License Summary Cards
The `Dashboard` component provides a set of summary cards to offer a quick overview of license-related statistics:

### Total License Card
- **Description:** Displays the total number of software licenses in your system.
- **Icon:** Archive icon (BsFillArchiveFill) is used to symbolize the card.
- **Navigation:** Clicking on this card allows users to navigate to the "TotalSoftwareDevices" page for detailed information about all licenses.

### About to Expire Card
- **Description:** Shows the count of licenses that are about to expire (less than 45 days remaining).
- **Icon:** Grid icon (BsFillGrid3X3GapFill) is used as the card's visual representation.
- **Navigation:** Clicking on this card enables users to access the "LessThan45Days" page to view licenses about to expire.

### Active License Card
- **Description:** Indicates the number of active licenses (more than 45 days remaining).
- **Icon:** People icon (BsPeopleFill) is used to represent the card.
- **Navigation:** Clicking on this card allows users to navigate to the "MoreThan45Days" page to explore active licenses.

### Expired License Card
- **Description:** Displays the count of licenses that have already expired.
- **Icon:** Bell icon (BsFillBellFill) is used to visually represent this card.
- **Navigation:** Clicking on this card allows users to access the "LessThanZeroDays" page to view expired licenses.

## Recent Orders
The `Dashboard` component provides a list of the most recent software orders, offering insights into recent software purchases:

- **Data Source:** The component fetches data from the API endpoint that provides information about recent software orders.
- **Columns:**
  - "Title": Software name
  - "Quantity": The number of software licenses purchased
  - "Price": Price of the software
  - "Purchase Date": Date of purchase
  - "License Key": Unique license key associated with the purchase
- **Pagination:** The list allows users to scroll through recent orders without pagination.

## Dashboard Chart
The chart within the `Dashboard` component provides a visual representation of various license-related actions:

- **Data Source:** The component fetches data from the API endpoint that provides action counts. These actions include purchases, renewals, and expirations.
- **Chart Type:** A bar chart created using Chart.js.
- **Data Visualization:** The chart displays action counts, allowing users to visually compare the frequency of different license-related actions.
- **Legend:** A legend is not displayed for this chart to maintain a clean and uncluttered appearance.

Please note that the accuracy of the data displayed in these features depends on the data fetched from the API endpoints. Ensure that the data source is up-to-date and reliable to provide accurate insights.


# Login Page Component

The `LoginPage` component is responsible for user authentication and login within your application. It provides a user-friendly form for entering login details and handles the submission of login credentials to the authentication server.

## Key Features

### Login Form
- The login form includes fields for entering a username and password.
- It validates user input, requiring both fields to be filled.
- Passwords are hidden by default but can be revealed using the "Show Password" feature.

### Show/Hide Password
- Users can toggle the visibility of the password input field.
- The "Show Password" button switches between revealing and hiding the password.

### Login Submit
- When a user submits the form, the component sends a request to the authentication server for user verification.
- If the authentication is successful, the user is redirected to the main dashboard.

## Functions

### `handleSubmit`
- Function to handle the form submission.
- Validates that both the username and password fields are filled.
- Calls the `login` function for authentication if validation passes.
- Clears the password and username fields after submission.

### `login`
- Initiates a POST request to the authentication server with the provided username and password.
- If the authentication server responds with a success status (HTTP 200), it saves the token in local storage.
- Upon successful authentication, the user is redirected to the main dashboard.

### `toggleShowPassword`
- Function to toggle the visibility of the password input field.
- Changes the input type between "text" (revealing the password) and "password" (hiding the password).

## Usage

- The `LoginPage` component should be included in your application's routing system for user authentication.
- Users access this page to log in to your application by providing their username and password.
- After successful login, users are redirected to the main dashboard or the desired destination.

Please ensure that the authentication server endpoint and the associated logic are properly configured to handle user authentication. Additionally, this component can be customized to match your application's design and branding.



# Manufacturer Component

The `Manufacturer` component serves as a central interface for managing manufacturer data within your application. It allows you to view, add, and update manufacturer information.

## Key Features

- **Manufacturer Table:** Displays a table listing manufacturer details, including their ID, name, field of work, company website link, email ID, and the option to edit each manufacturer's information. Users can select a manufacturer to edit by clicking the "Edit" button in the table. Data is fetched from the server and displayed in the table.

- **Add Manufacturer Form:** Allows users to input data for a new manufacturer, including their name, company website link, email ID, and field of work (software or hardware). Provides validation for required fields. Users can submit the form to add a new manufacturer to the database.

- **Update Manufacturer Form:** Displays a form pre-populated with the details of a selected manufacturer. Users can edit and update the manufacturer's information, including their name, company website link, email ID, and field of work. Upon submission, the updated data is sent to the server, and a success message is displayed.

## Components

### `ManufacturerForm`
- Sub-component responsible for rendering the "Add Manufacturer" form. Handles user input for adding a new manufacturer and sending data to the server. Displays success and error messages for user feedback.

### `UpdateManufacturerForm`
- Sub-component for updating manufacturer data. Pre-populates the form with the selected manufacturer's details. Allows users to edit and submit changes for the selected manufacturer. Displays success messages and handles errors during the update process.

## Usage

1. Include the `Manufacturer` component in your application's routing system for managing manufacturer data.
2. Users can view the list of manufacturers, add new manufacturers, and edit existing manufacturer details.
3. Ensure that your server-side API endpoints are correctly configured to handle manufacturer data and updates.

## Sample Usage

```jsx
import React from 'react';
import Manufacturer from './Manufacturer';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <Manufacturer />
    </div>
  );
}

export default App;
```



# Network Devices Component

The `NetworkDevices` component serves as a key interface for managing network devices within your application. It allows you to view, add, and delete network device records. Here's an explanation, sample usage, usage note, and important points regarding this component:

## Key Features

### Network Device Table
- Displays a table listing network device records, including device ID, device name, purchase date, asset number, warranty period, manufacturer name, and actions like analysis, RMA, and delete.
- Users can analyze or initiate RMA for a device or delete a network device record.
- Data is fetched from the server and displayed in the table.

### Add Network Device Form
- Allows users to input data for adding a new network device.
- Captures manufacturer information, including the manufacturer's name.
- Provides validation for required fields.
- Users can submit the form to add a new network device record.

## Components

### `NetworkDeviceForm`
- Sub-component responsible for rendering the "Add Network Device" form.
- Handles user input for adding a new network device and sending data to the server.
- Displays manufacturers in a dropdown for selection and allows users to add new manufacturers.
- Handles error and success feedback for the addition process.

## Usage

- The `NetworkDevices` component should be included in your application's routing system for managing network devices.
- Users can view the list of network devices, add new network devices, initiate analyses, and perform RMAs or delete network device records.
- Ensure that your server-side API endpoints are correctly configured to handle network device data and updates.

**Sample Usage:**

```jsx
import React from 'react';
import NetworkDevices from './NetworkDevices';

function App() {
  return (
    <div>
      <NetworkDevices />
    </div>
  );
}

export default App;

```

# NetworkServices Component

The `NetworkServices` component serves as a tabbed interface to manage network-related services. It provides tabs for RMA (Return Merchandise Authorization) and Network Analysis functionalities. Users can switch between these tabs to access the respective features.

## Key Features

- **Tabbed Interface:** Offers a tabbed user interface that allows users to switch between different network services seamlessly. The two tabs provided are:
  1. **RMA (Return Merchandise Authorization):** Enables users to initiate and manage return requests for network devices.
  2. **Network Analysis:** Provides access to tools and forms for analyzing network devices.

- **Dynamic Content:** The content of the component changes based on the selected tab, ensuring that users only see the relevant features and forms.

## Usage

1. Import the `NetworkServices` component into your application.
2. Integrate the `NetworkServices` component into your routing structure or within a page where you need to manage network services.

## Sample Usage

```jsx
import React from 'react';
import NetworkServices from './NetworkServices';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <NetworkServices />
    </div>
  );
}

export default App;

```


# NetworkDeviceAnalysisTable Component

The `NetworkDeviceAnalysisTable` component displays a table of network device analysis data, including device information, usage statistics, and manufacturer details. It's used to provide insights into network device performance and utilization.

## Key Features

- **Data Grid:** Renders network device analysis data in a tabular format with columns for device ID, active device status, average time usage, network device name, manufacturer name, quantity, cost, and company rating.

- **Data Retrieval:** Fetches network device analysis data from a server using an HTTP GET request. The data is loaded asynchronously upon component initialization.

- **Custom Columns:** The table includes custom columns that display information related to network device properties, such as device name, manufacturer, quantity, and cost. These columns make it easy to understand the data.

- **Pagination:** Provides pagination support to navigate through large datasets efficiently. Users can control the number of rows displayed per page.

## Usage

1. Import the `NetworkDeviceAnalysisTable` component into your application.
2. Place the `NetworkDeviceAnalysisTable` component in your desired route or page where you need to display network device analysis data.
3. Ensure that the server exposes an API endpoint (e.g., `http://localhost:8080/api/getNetworkDeviceAnalysis`) that returns the necessary data.

## Sample Usage

```jsx
import React from 'react';
import NetworkDeviceAnalysisTable from './NetworkDeviceAnalysisTable';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <NetworkDeviceAnalysisTable />
    </div>
  );
}

export default App;
```

# NetworkDeviceAnalysisForm Component

The `NetworkDeviceAnalysisForm` component allows users to submit network device analysis data. This form collects information such as network device ID, active device details, average time usage, and company rating. The form also performs validation checks on user inputs and handles the submission to the backend API.

## Key Features

- **Input Validation:** The component performs validation checks on user inputs to ensure that they are in the correct format.
  - Network Device ID: Must be selected from available options.
  - Active Device: A free-text input field.
  - Average Time Usage: Must be a valid number in hours (e.g., 1.5, 2, 3.5).
  - Company Rating: Must be a number between 1 and 5.

- **Submission Handling:** The form sends the analysis data to the backend API and handles success or failure responses, showing appropriate toast notifications to the user.

- **Reset Form:** The component provides a function to reset the form inputs after a successful or failed submission.

## Usage

1. Import the `NetworkDeviceAnalysisForm` component into your application.
2. Place the component within your application's user interface, where users need to submit network device analysis data.

## Sample Usage

```jsx
import React from 'react';
import NetworkDeviceAnalysisForm from './NetworkDeviceAnalysisForm';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <NetworkDeviceAnalysisForm />
    </div>
  );
}

export default App;
```
# NetworkDeviceRMAForm Component

The `NetworkDeviceRMAForm` component allows users to submit Network Device Return Merchandise Authorization (RMA) data. This form collects information such as network device ID, action type, amount, date of action, and the reason for the RMA request. The form also handles the submission of RMA data to the backend API and provides validation checks.

## Key Features

- **Input Validation:** The component performs validation checks on user inputs to ensure they are in the correct format.
  - Network Device ID: Must be selected from available options.
  - Action Type: Choose from Replace, Refund, or Repair.
  - Amount: A free-text input field for the amount.
  - Date of Action: A date input field.
  - Reason: A free-text input field for the reason.

- **Submission Handling:** The form sends the RMA data to the backend API and handles success or failure responses, showing appropriate toast notifications to the user.

- **Reset Form:** The component provides a function to reset the form inputs after a successful or failed submission.

## Usage

1. Import the `NetworkDeviceRMAForm` component into your application.
2. Place the component within your application's user interface, where users need to submit Network Device RMA data.

## Sample Usage

```jsx
import React from 'react';
import NetworkDeviceRMAForm from './NetworkDeviceRMAForm';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <NetworkDeviceRMAForm />
    </div>
  );
}

export default App;
```

# NetworkDeviceRMATable Component

The `NetworkDeviceRMATable` component displays a table of Network Device Return Merchandise Authorization (RMA) entries, allowing users to view and delete existing records. This component also includes a delete confirmation modal for added user interaction.

## Key Features

- **Data Retrieval:** The component fetches RMA data from an API endpoint, displaying it in a tabular format.
- **Delete Confirmation Modal:** When a user attempts to delete an RMA entry, a confirmation modal appears, requesting their confirmation before deletion.
- **Data Deletion:** When the user confirms deletion, the entry is removed from the table and the corresponding API endpoint.

## Usage

1. Import the `NetworkDeviceRMATable` component into your application.
2. Place the component within your application's user interface to display and manage Network Device RMA entries.

## Sample Usage

```jsx
import React from 'react';
import NetworkDeviceRMATable from './NetworkDeviceRMATable';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <NetworkDeviceRMATable />
    </div>
  );
}

export default App;
```

# SoftwareDevices Component

The `SoftwareDevices` component is designed to manage and display a list of software devices, allowing users to view details, and add new software entries.

## Key Features

- **Data Retrieval:** The component fetches software data from an API endpoint and displays it in a tabular format.
- **Tab Navigation:** Users can switch between tabs to view the list of software devices and add new software entries.
- **Days Left Indicator:** The component includes a "Days Left" column, providing a visual indicator of the remaining days until software expiration.
- **Software Form:** Users can add new software entries by navigating to the "Add software" tab.

## Usage

1. Import the `SoftwareDevices` component into your application.
2. Place the component within your application's user interface to display and manage software device entries.
3. Ensure that your API endpoint for fetching software data is correctly configured.

## Sample Usage

```jsx
import React from 'react';
import SoftwareDevices from './SoftwareDevices';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <SoftwareDevices />
    </div>
  );
}

export default App;
```
# LessThan45Days Component

The `LessThan45Days` component is responsible for displaying a table of software devices with fewer than 45 days left until expiration. It allows users to view and potentially renew or delete software entries.

## Key Features

- **Data Retrieval:** The component fetches software data from an API endpoint and displays it in a table.
- **Days Left Indicator:** It calculates the days left until software expiration and provides a visual indicator.
- **Renewal Option:** For software entries with less than 45 days left, users can click the "Renew" button to renew the software.
- **Deletion:** Users can delete software entries with a confirmation modal.

## Usage

1. Import the `LessThan45Days` component into your application.
2. Place the component within your application's user interface to display software entries with fewer than 45 days left.

### Sample Usage

```jsx
import React from 'react';
import LessThan45Days from './LessThan45Days';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <LessThan45Days />
    </div>
  );
}

export default App;
```

# LessThan45Days Component

The `LessThan45Days` component is responsible for displaying a table of software devices with fewer than 45 days left until expiration. It allows users to view and potentially renew or delete software entries.

## Key Features

- **Data Retrieval:** The component fetches software data from an API endpoint and displays it in a table.
- **Days Left Indicator:** It calculates the days left until software expiration and provides a visual indicator.
- **Renewal Option:** For software entries with less than 45 days left, users can click the "Renew" button to renew the software.
- **Deletion:** Users can delete software entries with a confirmation modal.

## Usage

1. Import the `LessThan45Days` component into your application.
2. Place the component within your application's user interface to display software entries with fewer than 45 days left.

### Sample Usage

```jsx
import React from 'react';
import LessThan45Days from './LessThan45Days';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <LessThan45Days />
    </div>
  );
}

export default App;
```

# LessThanZeroDays Component

The `LessThanZeroDays` component is responsible for displaying a table of software devices with zero days left until expiration. It allows users to view, renew, send email notifications, or delete software entries.

## Key Features

- **Data Retrieval:** The component fetches software data with zero days left until expiration from an API endpoint and displays it in a table.
- **Email Notification:** Users can click the "Send Mail" button to trigger email notifications for software renewal.
- **Renewal Option:** For software entries with zero days left, users can click the "Renew" button to renew the software.
- **Deletion:** Users can delete software entries with a confirmation modal.

## Usage

1. Import the `LessThanZeroDays` component into your application.
2. Place the component within your application's user interface to display software entries with zero days left.

### Sample Usage

```jsx
import React from 'react';
import LessThanZeroDays from './LessThanZeroDays';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <LessThanZeroDays />
    </div>
  );
}

export default App;

```
# MoreThan45Days Component

The `MoreThan45Days` component is responsible for displaying a table of software devices with more than 45 days left until expiration. It allows users to view, change plans, and delete software entries.

## Key Features

- **Data Retrieval:** The component fetches software data with more than 45 days left until expiration from an API endpoint and displays it in a table.
- **Change Plan Option:** Users can click the "Change Plan" button to navigate to the software change plan form.
- **Deletion:** Users can delete software entries with a confirmation modal.

## Usage

1. Import the `MoreThan45Days` component into your application.
2. Place the component within your application's user interface to display software entries with more than 45 days left.

### Sample Usage

```jsx
import React from 'react';
import MoreThan45Days from './MoreThan45Days';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <MoreThan45Days />
    </div>
  );
}

export default App;
```
# SoftwareForm Component

The `SoftwareForm` component is responsible for adding software information to your application. It allows users to input details such as software name, purchase date, expiry date, type of plan, quantity, price of software, version, and license key.

## Understanding

- **Manufacturer**: Users can select a manufacturer from the list of publishers, which is dynamically fetched from an API. They can also add a new publisher.
- **Field of Work**: The "Field of Work" is set to "Software" and is read-only.
- **Software Name**: Users can enter the name of the software.
- **License Key**: Users can input the software's license key.
- **Version**: The software's version can be specified.
- **Purchase Date**: Users can select the date when the software was purchased.
- **Expiry Date**: Users can choose the date when the software expires.
- **Type of Plan**: Users can select the software's plan type, such as Free, Basic, Pro, Premium, or Enterprise.
- **Quantity**: The total number of licenses available for the software.
- **Price of Software**: Users can provide the price of the software.

## Key Features

- **Data Input**: The form allows users to input software details, including selecting a manufacturer and choosing a plan type.
- **Validation**: The form validates that all required fields are filled in before submission.
- **Publisher Selection**: Users can choose a publisher from the list or add a new one.
- **Success Message**: Upon successful software addition, a success message is displayed.
- **Error Handling**: In case of an error, an error message is shown.
- **Dynamic Manufacturer List**: The list of manufacturers is dynamically fetched from the API.
- **Disabled Submission**: The "Add Software" button is disabled until all required fields are filled.

## Usage

1. Import the `SoftwareForm` component into your application.
2. Integrate it within your application to allow users to input software details.

### Sample Usage

```jsx
import React from 'react';
import SoftwareForm from './SoftwareForm';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <SoftwareForm />
    </div>
  );
}

export default App;
```
# Total Software Devices

The "Total Software Devices" component is part of a software management system. It displays a table of software devices, allowing users to view and manage software-related information. This component is built with React and uses the Material-UI and Ant Design libraries for the user interface.

## Key Features
- View a list of software devices, including details like software name, type of plan, publisher name, users allowed, and more.
- Analyze software data by clicking on the "Analyze" button for each software device.
- Add new software devices by clicking on the "Add Software" button.
- Delete software devices by confirming the deletion in a modal dialog.
- An option to perform analysis on each software device.

## Usage
The "Total Software Devices" component is used to display and manage software devices within the software management system. It can be included in the main user interface of the application.

## Sample Usage
```jsx
import React from 'react';
import TotalSoftwareDevices from './TotalSoftwareDevices';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <TotalSoftwareDevices />
      {/* Other components and content */}
    </div>
  );
}

export default App;
```

# Services

The "Services" component provides a user interface for interacting with different software services. It allows users to choose between multiple services, such as renewing software, changing software plans, and accessing software analysis. This component is built using React and leverages Ant Design and Material-UI components for UI elements.

## Key Features
- Users can switch between different service options using tabs.
- The available services include software renewal, changing software plans, and accessing software analysis.
- The component allows a clean and organized user interface for service selection.

## Usage
The "Services" component is designed for providing a user interface for software services within your application. It can be used as a part of your application's dashboard or navigation menu.

## Sample Usage
```jsx
import React from 'react';
import Services from './Services';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <Services />
      {/* Other components and content */}
    </div>
  );
}

export default App;

```

# Software Analyse Table

The "Analyse Table" component is designed to display and analyze data in a tabular format. It offers the ability to switch between two different data sets for software and network devices analysis. This component is built using React and utilizes Ant Design and Material-UI libraries for creating the user interface.

## Key Features
- View and analyze data in a tabular format.
- Switch between two tabs to view software and network device analysis.
- Data for each tab is fetched from different APIs for analysis.
- The "Software" tab displays various software-related data fields, including software name, average time usage, active users, company rating, software ID, manufacturer name, and purchased licenses.
- The "Network Device" tab displays analysis data for network devices.

## Usage
The "Analyse Table" component is suitable for displaying and analyzing tabular data with the ability to switch between different data sets. It can be integrated into the user interface of your application.

## Sample Usage
```jsx
import React from 'react';
import AnalyseTable from './AnalyseTable';

function App() {
  return (
    <div>
      {/* Other components and content */}
      <AnalyseTable />
      {/* Other components and content */}
    </div>
  );
}

export default App;
```

# Renew Software Form

The "Renew Software Form" component provides a form for users to renew their software subscriptions. It is a part of the software services section in your application. This component is built using React and Material-UI components for user interaction.

## Key Features
- Users can select a software from a list of available software devices for renewal.
- Users can input the purchase date, expiry date, and license key for renewal.
- The form provides validation and displays success or error messages accordingly.
- Renewal requests are sent to the server with proper authorization.

## Usage
The "Renew Software Form" component can be used as part of a larger interface for software services. Users can access this form to renew their software subscriptions. The component can be integrated with other software management features and forms.

## Sample Usage
```jsx
import React from 'react';
import RenewSoftwareForm from './RenewSoftwareForm';

function SoftwareServices() {
  return (
    <div>
      {/* Other components and content */}
      <RenewSoftwareForm />
      {/* Other components and content */}
    </div>
  );
}

export default SoftwareServices;

```

# Software Analysis Form

The "Software Analysis Form" is a component that allows users to perform software analysis. It enables users to provide details about a specific software, such as active users, average time usage, and company rating. This form is designed to help users analyze the performance and usage of software devices.

## Key Features
- Users can select a software device for analysis.
- Users can input the number of active users, average time usage, and company rating for the selected software.
- The form validates user input and displays success or error messages.
- Analysis data is sent to the server for processing.

## Usage
The "Software Analysis Form" can be used as part of a larger software management system, enabling users to evaluate software performance and usage. It should be integrated with other software management features and forms to provide a comprehensive software management experience.

## Sample Usage
```jsx
import React from 'react';
import SoftwareAnalysisForm from './SoftwareAnalysisForm';

function SoftwareManagement() {
  return (
    <div>
      {/* Other components and content */}
      <SoftwareAnalysisForm />
      {/* Other components and content */}
    </div>
  );
}

export default SoftwareManagement;
```
# Software Change Plan Form

The "Software Change Plan Form" is a component that allows users to change the plan of a specific software. It enables users to modify plan-related details, such as license key, expiry date, purchase date, type of plan, quantity, and price of software. This form is designed to facilitate plan adjustments for software devices.

## Key Features
- Users can select a software device for plan modification.
- Users can update details like license key, expiry date, purchase date, type of plan, quantity, and price of software.
- The form validates user input and displays success or error messages.
- Plan change data is sent to the server for processing.

## Usage
The "Software Change Plan Form" can be used as part of a comprehensive software management system, allowing users to make necessary changes to the plans of software devices. It should be integrated with other software management features and forms to provide complete software management functionality.

## Sample Usage
```jsx
import React from 'react';
import SoftwareChangePlanForm from './SoftwareChangePlanForm';

function SoftwareManagement() {
  return (
    <div>
      {/* Other components and content */}
      <SoftwareChangePlanForm />
      {/* Other components and content */}
    </div>
  );
}

export default SoftwareManagement;
