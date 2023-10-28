import React, { useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Button, Space, Typography } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";
import { Modal } from "antd"; // Import Modal from Ant Design
import DeleteIcon from '@mui/icons-material/Delete';
import AutorenewIcon from '@mui/icons-material/Autorenew';
import MailOutlineIcon from '@mui/icons-material/MailOutline';

function LessThanZeroDays() {
  const [softwareList, setSoftwareList] = useState([]);
  const [isEmailSent, setIsEmailSent] = useState(false);
  const hasMounted = useRef(false);
  const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);

  useEffect(() => {
    if (!hasMounted.current) {
      axios.get('http://localhost:8080/api/getSoftwareLessThanZerodays',{headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }})
        .then((response) => {
          setSoftwareList(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
    hasMounted.current = true;
  }, []);

  const handleSendMailClick = () => {
    axios.get('http://localhost:8080/api/sendMail',{headers: {
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    }})
      .then((response) => {
        setIsEmailSent(true);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleDeleteClick = (softwareId) => {
    setSoftwareToDelete(softwareId);
    setDeleteConfirmationVisible(true);
  };

  const handleDeleteConfirm = () => {
    if (softwareToDelete !== null) {
      axios
        .delete(`http://localhost:8080/api/deleteSoftware/${softwareToDelete}`,{headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }})
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

  const containerStyle = {
    padding: '20px', // Add padding to the container
    backgroundColor: '#f0f0f0', // Add a background color
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
              <TableCell>Renew</TableCell>
              <TableCell>Send Mail</TableCell>
              <TableCell>Delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {softwareList.map((software) => {
              return (
                <TableRow key={software.id}>
                  <TableCell>{serialNumber++}</TableCell>
                  <TableCell>{software.id}</TableCell>
                  <TableCell>{software.softwareName}</TableCell>
                  <TableCell>{software.typeOfPlan}</TableCell>
                  <TableCell>{software.manufacturer.name}</TableCell>
                  <TableCell>{software.quantity}</TableCell>
                  <TableCell>Expired</TableCell>
                  <TableCell>
                    <Link to="/Services">
                      <Button type="primary" style={{ marginRight: "10px" }}>
                        <AutorenewIcon />
                      </Button>
                    </Link>
                    </TableCell>
                    <TableCell>
                    <Button type="primary" onClick={handleSendMailClick}>
                      <MailOutlineIcon />
                    </Button>
                    </TableCell>
                  <TableCell>
                    <Button type="primary" onClick={() => handleDeleteClick(software.id)}>
                    <DeleteIcon />
                    </Button>
                  </TableCell>
                </TableRow>
              );
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

export default LessThanZeroDays;
