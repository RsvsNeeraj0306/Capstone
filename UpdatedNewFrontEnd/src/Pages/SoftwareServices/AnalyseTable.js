import { Space, Typography, Tabs } from "antd";
import { useEffect, useState } from "react";
import axios from "axios";
import { Tab } from "@mui/material"; // Import Tabs and Tab from Material-UI
import { DataGrid } from "@mui/x-data-grid";
import NetworkDeviceAnalysisTable from "../NetworkServices/ NetworkDeviceAnalysisTable";

function AnalyseTable() {
  const [activeTab, setActiveTab] = useState("AnalyseTable"); // Set the initial active tab
  const [softwareList, setSoftwareList] = useState([]);

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  const containerStyle = {
    padding: "20px", // Add padding to the container
    backgroundColor: "#f0f0f0", // Add a background color
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/getSoftwareAnalysis", {
        headers: {
          "Authorization": "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 100 },
    { field: "averageTimeUsage", headerName: "Average Time Usage", width: 150 },
    { field: "activeUsers", headerName: "Average Users", width: 150 },
    { field: "companyRating", headerName: "Company Rating", width: 150 },
    {
      field: "software.id",
      headerName: "Software ID",
      width: 120,
      valueGetter: (params) => params.row.software.id,
    },
    {
      field: "software.softwareName",
      headerName: "Software Name",
      width: 120,
      valueGetter: (params) => params.row.software.softwareName,
    },
    {
      field: "software.manufacturer.name",
      headerName: "Manufacturer Name",
      width: 200,
      valueGetter: (params) => params.row.software.manufacturer.name,
    },
    {
      field: "software.quantity",
      headerName: "Purchased Licenses",
      width: 200,
      valueGetter: (params) => params.row.software.quantity,
    },
  ];

  return (
    <div style={containerStyle}>
      <Typography.Title level={4}>Analyse Table</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="AnalyseTable" tab="Software" />
        <Tab key="NetworkDeviceAnalysisTable" tab="Network Device" />
      </Tabs>
      {
      activeTab === "AnalyseTable" && (
        <div style={{ height: 400, width: "100%" }}>
          <DataGrid
            rows={softwareList}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 5 },
              },
            }}
            pageSizeOptions={[5, 10]}
          />
        </div>
      )}
      {activeTab === "NetworkDeviceAnalysisTable" && <NetworkDeviceAnalysisTable />}

    </div>
  );
}

export default AnalyseTable;
