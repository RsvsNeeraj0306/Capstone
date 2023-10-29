import { Space, Typography, Tabs} from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI



function ManufacturerHistory() {
    const [manufacturerList, setManufacturerList] = useState([]);
    const [activeTab, setActiveTab] = useState("manufacturerHistory");
    
    const handleChangeTab = (key) => {
        setActiveTab(key);
    };
    
    useEffect(() => {
        // Fetch manufacturer data from your API or backend here
        axios.get('http://localhost:8080/api/getManufacturerHistory',{headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }})
        .then((response) => {
            setManufacturerList(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
    }, []);
    
    let serialNumber = 1; // Initialize the serial number
    
    return (
        <TableContainer sx={{ maxHeight: 440 }}>
         <Table stickyHeader aria-label="sticky table">
                <TableHead>
                <TableRow>
                    <TableCell>Serial Number</TableCell>
                    <TableCell>Manufacturer Name</TableCell>
                    <TableCell>Manufacturer Email</TableCell>
                    <TableCell>Manufacturer Website</TableCell>
                    <TableCell>Field Of Work</TableCell>
                    <TableCell>Action</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {manufacturerList.map((manufacturer) => (
                    <TableRow key={manufacturer.id}>
                    <TableCell>{serialNumber++}</TableCell>
                    <TableCell>{manufacturer.name}</TableCell>
                    <TableCell>{manufacturer.emailId}</TableCell>
                    <TableCell>{manufacturer.companyWebsiteLink}</TableCell>
                    <TableCell>{manufacturer.fieldOfWork}</TableCell>
                    <TableCell>{manufacturer.action}</TableCell>
                </TableRow>
                ))}
            </TableBody>
            </Table>
        </TableContainer>
    );
}

export default ManufacturerHistory;
