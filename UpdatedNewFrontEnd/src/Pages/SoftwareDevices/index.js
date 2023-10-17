import { Space, Typography, Tabs} from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI
import MoreThan45Days from "./MoreThan45days";

function SoftwareDevices() {
  const [softwareList, setSoftwareList] = useState([]);
  const [activeTab, setActiveTab] = useState("softwareDevices");

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  useEffect(() => {
    // Fetch software data from your API or backend here
    axios.get('http://localhost:8080/api/SelectedSoftware')  // Adjust the API endpoint as needed
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="softwareDevices" tab="Total Software" />
        <Tab key="moreThan45Days" tab="More Than 45" />
      </Tabs>
      {activeTab === "softwareDevices" && (
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Software Name</TableCell>
                <TableCell>Purchase Date</TableCell>
                <TableCell>Expiry Date</TableCell>
                <TableCell>Type of Plan</TableCell>
                <TableCell>Total Licenses</TableCell>
                <TableCell>Price of Software</TableCell>
                <TableCell>Manufacturer Name</TableCell>
                <TableCell>Days Left to Expiry</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {softwareList.map((software) => (
                <TableRow key={software.id}>
                  <TableCell>{software.softwareName}</TableCell>
                  <TableCell>{software.purchaseDate}</TableCell>
                  <TableCell>{software.expiryDate}</TableCell>
                  <TableCell>{software.typeOfPlan}</TableCell>
                  <TableCell>{software.usersCanUse}</TableCell>
                  <TableCell>{software.priceOfSoftware}</TableCell>
                  <TableCell>{software.manufacturer.name}</TableCell>
                  <TableCell>
                    <DaysLeftCount daysLeft={calculateDaysLeft(software.expiryDate)} />
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      {activeTab === "moreThan45Days" && <MoreThan45Days />} {/* Render MoreThan45Days component */}
    </Space>
  );

  function calculateDaysLeft(expiryDate) {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    const daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
    return daysLeft;
  }
}

function DaysLeftCount({ daysLeft }) {
  let containerColor = "green"; // Default color

  if (daysLeft < 30) {
    containerColor = "red";
  } else if (daysLeft < 45) {
    containerColor = "yellow";
  }

  return (
    <div style={{ backgroundColor: containerColor, display: "inline", padding: "4px 8px", borderRadius: "4px" }}>
      {daysLeft >= 0 ? daysLeft : 'Expired'}
    </div>
  );
}

export default SoftwareDevices;
