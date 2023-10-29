import { Space, Typography, Tabs} from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI


function SoftwareHistory() {
    const [softwareList, setSoftwareList] = useState([]);
    const [activeTab, setActiveTab] = useState("softwareHistory");
    
    const handleChangeTab = (key) => {
        setActiveTab(key);
    };
    
    useEffect(() => {
        // Fetch software data from your API or backend here
        axios.get('http://localhost:8080/api/getSoftwareHistory',{headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }})// Adjust the API endpoint as needed
        .then((response) => {
            setSoftwareList(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
    }, []);
    
    let serialNumber = 1; // Initialize the serial number
    
    return (
        <TableContainer sx={{ maxHeight: 440 }}>
        <Typography.Title level={4}>Software History</Typography.Title>
         <TableContainer sx={{ maxHeight: 440 }}>
         <Table stickyHeader aria-label="sticky table">
                <TableHead>
                <TableRow>
                    <TableCell>Serial Number</TableCell>
                    <TableCell>Software Name</TableCell>
                    <TableCell>Purchase Date</TableCell>
                    <TableCell>Expiry Date</TableCell>
                    <TableCell>Type of Plan</TableCell>
                    <TableCell>Total Licenses</TableCell>
                    <TableCell>Version</TableCell>
                    <TableCell>Price of Software</TableCell>

                    <TableCell>Action</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {softwareList.map((software) => (
                    <TableRow key={software.id}>
                    <TableCell>{serialNumber++}</TableCell>
                    <TableCell>{software.softwareName}</TableCell>
                    <TableCell>{software.purchaseDate}</TableCell>
                    <TableCell>{software.expiryDate}</TableCell>
                    <TableCell>{software.typeOfPlan}</TableCell>
                    <TableCell>{software.quantity}</TableCell>
                    <TableCell>{software.version}</TableCell>
                    <TableCell>{software.priceOfSoftware}</TableCell>
                    <TableCell>{software.action}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
        </TableContainer>
        </TableContainer>
    );
    }

export default SoftwareHistory;