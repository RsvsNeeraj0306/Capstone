import { Space, Typography, Tabs } from "antd";
import { useState,useEffect } from "react";
import axios from "axios";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI
import ManufacturerHistory from "./ManufacturerHistory";
import SoftwareHistory from "./SoftwareHistory";


function AllHistory(){
    const [activeTab, setActiveTab] = useState("AllHistory");
    const [networkHistory, setNetworkHistory]= useState([]);

    const handleChangeTab = (key) => {
        setActiveTab(key);
    };

    useEffect(() => {
        // Fetch hardware data from your API or backend here
        axios.get('http://localhost:8080/api/getNetworkDeviceHistory')
          .then((response) => {
            setNetworkHistory(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }, []);

        let serialNumber = 1; // Initialize the serial number

        return (
            <Space size={20} direction="vertical">
                <Typography.Title level={4}>Network Devices History</Typography.Title>
                <Tabs activeKey={activeTab} onChange={handleChangeTab}>
                    <Tab key="AllHistory" tab="Network Device History" />
                    <Tab key="ManufacturerHistory" tab="Manufacturer History" />
                    <Tab key="SoftwareHistory" tab="Software History" />
                </Tabs>
                {activeTab === "AllHistory" && (
                 <TableContainer sx={{ maxHeight: 440 }}>
                 <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                        <TableCell>Serial Number</TableCell>
                        <TableCell>Device Name</TableCell>
                        <TableCell>Purchase Date</TableCell>
                        <TableCell>Warranty Period</TableCell>
                        <TableCell>TypeOfAction</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {networkHistory.map((networkHistory) => (
                        <TableRow key={networkHistory.id}>
                            <TableCell>{serialNumber++}</TableCell>
                            <TableCell>{networkHistory.hardwareName}</TableCell>
                            <TableCell>{networkHistory.purchaseDate}</TableCell>
                            <TableCell>{networkHistory.warrantyEndDate}</TableCell>
                            <TableCell>{networkHistory.action}</TableCell>
                        </TableRow>
                        ))}
                    </TableBody>
                    </Table>
                </TableContainer>
                )}
                {activeTab === "ManufacturerHistory" && <ManufacturerHistory />}
                {activeTab === "SoftwareHistory" && <SoftwareHistory />}
            </Space>
        );
}

export default AllHistory;
