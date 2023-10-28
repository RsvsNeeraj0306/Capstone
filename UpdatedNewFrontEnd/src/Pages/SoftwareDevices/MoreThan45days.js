import { Space, Typography } from "antd";
import { useEffect, useState } from "react";
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import axios from "axios";
import { Link } from "react-router-dom";
import { Modal } from "antd"; // Import Modal from Ant Design
import DeleteIcon from '@mui/icons-material/Delete';
import ChangeCircleIcon from '@mui/icons-material/ChangeCircle';

function MoreThan45Days() {
  const [softwareList, setSoftwareList] = useState([]);
  const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);

  useEffect(() => {
    // Fetch software data from your API or backend here
    axios.get('http://localhost:8080/api/allSoftware',{headers: {
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    }})  // Adjust the API endpoint as needed
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  // Function to calculate days left until the expiry date
  const calculateDaysLeft = (expiryDate) => {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    const daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
    return daysLeft;
  };

  let serialNumber = 1; // Initialize the serial number

  const containerStyle = {
    padding: '20px', // Add padding to the container
    backgroundColor: '#f0f0f0', // Add a background color
  };

  const handleDeleteClick = (softwareId) => {
    setSoftwareToDelete(softwareId);
    setDeleteConfirmationVisible(true);
  };

  const handleDeleteConfirm = () => {
    if (softwareToDelete !== null) {
      axios
        .delete(`http://localhost:8080/api/deleteSoftware/${softwareToDelete}`, {
          headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }
        })
        .then(() => {
          setSoftwareList((prevSoftwareList) =>
            prevSoftwareList.filter((software) => software.id !== softwareToDelete)
          );
        })
        .catch((error) => {
          console.error(error);
        });
    }
    setDeleteConfirmationVisible(false);
  };

  const handleDeleteCancel = () => {
    setDeleteConfirmationVisible(false);
  };

  return (
    <div style={containerStyle}>
      <Space size={20} direction="vertical">
        <Typography.Title level={4}>Software Devices</Typography.Title>
        <TableContainer sx={{ maxHeight: 440 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell>Serial Number</TableCell>
                <TableCell>ID</TableCell>
                <TableCell>Software Name</TableCell>
                <TableCell>Type of Plan</TableCell>
                <TableCell>Publisher Name</TableCell>
                <TableCell>Users Can Use</TableCell>
                <TableCell>Days Left</TableCell>
                <TableCell>changePlan</TableCell>
                <TableCell>Delete</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {softwareList.map((software) => {
                const daysLeft = calculateDaysLeft(software.expiryDate);
                if (daysLeft > 45) {
                  return (
                    <TableRow key={software.id}>
                      <TableCell>{serialNumber++}</TableCell>
                      <TableCell>{software.id}</TableCell>
                      <TableCell>{software.softwareName}</TableCell>
                      <TableCell>{software.typeOfPlan}</TableCell>
                      <TableCell>{software.manufacturer.name}</TableCell>
                      <TableCell>{software.quantity}</TableCell>
                      <TableCell>{daysLeft}</TableCell>
                      <TableCell>
                        <Link to="/SoftwareChangePlanForm">
                          <Button variant="contained" color="primary">
                            <ChangeCircleIcon />
                          </Button>
                        </Link>
                      </TableCell>
                      <TableCell>
                        <Button type="primary" onClick={() => handleDeleteClick(software.id)}>
                         <DeleteIcon />
                        </Button>
                      </TableCell>
                    </TableRow>
                  );
                }
                return null; // Skip this software
              })}
            </TableBody>
          </Table>
        </TableContainer>

        {/* Confirmation Modal */}
        <Modal
          title="Confirm Deletion"
          visible={deleteConfirmationVisible}
          onOk={handleDeleteConfirm}
          onCancel={handleDeleteCancel}
        >
          <p>Are you sure you want to delete this software?</p>
        </Modal>
      </Space>
    </div>
  );
}

export default MoreThan45Days;
