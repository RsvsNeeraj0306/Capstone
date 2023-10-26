import React, { useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Button, Space, Typography } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";
import { Modal } from "antd"; // Import Modal from Ant Design

function LessThanZeroDays() {
  const [softwareList, setSoftwareList] = useState([]);
  const [isEmailSent, setIsEmailSent] = useState(false);
  const hasMounted = useRef(false);
  const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);

  useEffect(() => {
    if (!hasMounted.current) {
      axios.get('http://localhost:8080/api/getSoftwareLessThanZerodays')
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
    axios.get('http://localhost:8080/api/sendMail')
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
        .delete(`http://localhost:8080/api/deleteSoftware/${softwareToDelete}`)
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
              <TableCell>Actions</TableCell>
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
                        Renew
                      </Button>
                    </Link>
                    </TableCell>
                    <TableCell>
                    <Button type="primary" onClick={handleSendMailClick}>
                      Send Mail
                    </Button>
                    </TableCell>
                  <TableCell>
                    <Button type="primary" onClick={() => handleDeleteClick(software.id)}>
                      Delete
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
  );
}

export default LessThanZeroDays;
