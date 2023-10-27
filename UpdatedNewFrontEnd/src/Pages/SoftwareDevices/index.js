import { Space, Typography, Tabs} from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI
import Chip from '@mui/joy/Chip';
import { DataGrid } from '@mui/x-data-grid';

import SoftwareForm from "./SoftwareForm";


function SoftwareDevices() {
  const [softwareList, setSoftwareList] = useState([]);
  const [activeTab, setActiveTab] = useState("softwareDevices");

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  useEffect(() => {
    // Fetch software data from your API or backend here
    axios.get('http://localhost:8080/api/allSoftware')  // Adjust the API endpoint as needed
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  let serialNumber = 1; // Initialize the serial number

  const columns = [
    { field: 'id', headerName: 'ID', width: 10 },
    { field: 'softwareName', headerName: 'Software Name', width: 150 },
    { field: 'purchaseDate', headerName: 'Purchase Date', width: 150 },
    { field: 'expiryDate', headerName: 'Expiry Date', width: 120 },
    { field: 'typeOfPlan', headerName: 'Type of Plan', width: 100 },
    { field: 'quantity', headerName: 'Total Licenses', width: 120 },
    { field: 'version', headerName: 'Version', width: 100 },
    { field: 'priceOfSoftware', headerName: 'Price of Software', width: 100 },
    { field: 'daysLeft', headerName: 'Days Left', width: 100, renderCell: (params) => (
      <Chip color={calculateDaysLeft(params.row.expiryDate).status} 
            size="lg"
            variant="soft">{calculateDaysLeft(params.row.expiryDate).daysLeft}</Chip>
    )},


  ];

  return (
      <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="softwareDevices" tab="Total Software" />
        <Tab key="softwareForm" tab="Add software" />
      </Tabs>
      {activeTab === "softwareDevices" && (
         <div style={{ height: 400, width: '100%' }}>
         <DataGrid
           rows={softwareList}
           columns={columns}
           initialState={{
             pagination: {
               paginationModel: { page: 0, pageSize: 5 },
             },
           }}
           pageSizeOptions={[5, 10]}
           checkboxSelection
         />
       </div>
      )}
      {activeTab === "softwareForm" && <SoftwareForm />} {/* Render MoreThan45Days component */}
    </Space>
  );

  function calculateDaysLeft(expiryDate) {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    let daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    let status;
  
    if (daysLeft > 45) {
      status = "success";
    } else if (daysLeft > 0) {
      status = "warning";
    } else {
      status = "danger";
      daysLeft = 0;
    }
  
    return { daysLeft, status };
  }

}


 

export default SoftwareDevices;
