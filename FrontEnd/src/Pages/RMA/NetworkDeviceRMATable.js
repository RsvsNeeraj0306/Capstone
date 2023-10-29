import { Tabs, Typography, Table, Modal } from 'antd';
import React, { useState, useEffect } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';

const NetworkDeviceRMATable = () => {
  const [networkDeviceRMAData, setNetworkDeviceRMAData] = useState([]);
  const [activeTab, setActiveTab] = useState('NetworkDeviceRMATable');
  const [deleteModalVisible, setDeleteModalVisible] = useState(false);
  const [deletingEntryId, setDeletingEntryId] = useState(null);

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  const containerStyle = {
    padding: '20px',
    backgroundColor: '#f0f0f0',
  };

  useEffect(() => {
    // Fetch data from your API endpoint
    fetch('http://localhost:8080/api/getNetworkDeviceRMA', {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then((response) => response.json())
      .then((data) => setNetworkDeviceRMAData(data))
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  const showDeleteModal = (id) => {
    setDeletingEntryId(id);
    setDeleteModalVisible(true);
  };

  const handleDelete = () => {
    // Make an API call to delete the entry with the specified ID
    fetch(`http://localhost:8080/api/deleteNetworkDeviceRMA/${deletingEntryId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then((response) => {
        if (response.status === 200) {
          // If the delete is successful, update the table by filtering out the deleted entry
          setNetworkDeviceRMAData((prevData) =>
            prevData.filter((entry) => entry.id !== deletingEntryId),
          );

        } else {
          console.error('Error deleting entry');
          // Show an error toast notification
          toast.error('Error deleting entry');
        }
      })
      .catch((error) => {
        console.error('Error deleting entry:', error);
        // Show an error toast notification
        toast.error('Error deleting entry');
      });

    setDeleteModalVisible(false);
  };

  const hideDeleteModal = () => {
    setDeleteModalVisible(false);
  };

  return (
    <div style={containerStyle}>
      <Typography.Title level={4}>RMA</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tabs.TabPane key="NetworkDeviceRMATable" tab="Network Device RMA" />
      </Tabs>
      {activeTab === 'NetworkDeviceRMATable' && (
        <div>
          <Typography.Title level={4}>Network Device RMA Table</Typography.Title>
          <Table
            dataSource={networkDeviceRMAData}
            rowKey="id"
            scroll={{ y: 440 }}
          >
            <Table.Column title="Serial Number" dataIndex="id" key="id" />
            <Table.Column title="ActionType" dataIndex="actionType" key="actionType" />
            <Table.Column title="Amount" dataIndex="amount" key="amount" />
            <Table.Column title="DateOfAction" dataIndex="dateOfAction" key="dateOfAction" />
            <Table.Column title="Reason" dataIndex="reason" key="reason" />
            <Table.Column
              title="Network Device ID"
              dataIndex={["networkDevice", "id"]}
              key="networkDeviceId"
            />
            <Table.Column
              title="Delete"
              key="delete"
              render={(text, record) => (
                <button className="form-button" onClick={() => showDeleteModal(record.id)}>
                  <CheckCircleOutlineIcon />
                </button>
              )}
            />
          </Table>
        </div>
      )}
      <ToastContainer />

      {/* Delete Confirmation Modal */}
      <Modal
        title="Confirm Delete"
        visible={deleteModalVisible}
        onOk={handleDelete}
        onCancel={hideDeleteModal}
        okText="Delete"
        cancelText="Cancel"
      >
        <p>Are you sure you want to delete this entry?</p>
      </Modal>
    </div>
  );
};

export default NetworkDeviceRMATable;
