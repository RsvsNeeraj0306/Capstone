import { Space, Typography } from "antd";
import { useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button } from "@mui/material";
import axios from "axios";
import { Link } from "react-router-dom";
import { Modal } from "antd"; // Import Modal from Ant Design
import DeleteIcon from '@mui/icons-material/Delete';
import AutorenewIcon from '@mui/icons-material/Autorenew';

function LessThan45Days() {
  const [softwareList, setSoftwareList] = useState([]);
  const hasMounted = useRef(false);
  const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);

  useEffect(() => {
    if (hasMounted.current) {
      axios.get('http://localhost:8080/api/allSoftware', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      })
        .then((response) => {
          setSoftwareList(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
    return () => {
      hasMounted.current = true;
    }
  }, []);

  const containerStyle = {
    padding: '20px',
    backgroundColor: '#f0f0f0',
  };

  const calculateDaysLeft = (expiryDate) => {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    const daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
    return daysLeft;
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
          console.log(setSoftwareList);
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

  let serialNumber = 1;

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
                <TableCell>Renew</TableCell> {/* New column for Renew button */}
                <TableCell>Delete</TableCell> {/* New column for Delete button */}
              </TableRow>
            </TableHead>
            <TableBody>
              {softwareList.map((software) => {
                const daysLeft = calculateDaysLeft(software.expiryDate);
                if (daysLeft < 45 && daysLeft >= 0) {
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
                        <Link to="/RenewSoftwareForm">
                          <Button variant="contained" color="primary">
                            <AutorenewIcon />
                          </Button>
                        </Link>
                      </TableCell>
                      <TableCell>
                        <Button variant="contained" color="primary" onClick={() => handleDeleteClick(software.id)}>
                          <DeleteIcon />
                        </Button>
                      </TableCell>
                    </TableRow>
                  );
                }
                return null;
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

export default LessThan45Days;
