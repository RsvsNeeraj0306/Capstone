import React, { useEffect, useState } from 'react';
import { Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';

function NetworkDeviceAnalysisTable() {
  const [networkDeviceAnalysisList, setNetworkDeviceAnalysisList] = useState([]);

  const containerStyle = {
    padding: "20px", // Add padding to the container
    backgroundColor: "#f0f0f0", // Add a background color
  };

  useEffect(() => {
    axios.get('http://localhost:8080/api/getNetworkDeviceAnalysis', {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
      },
    })
      .then((response) => {
        setNetworkDeviceAnalysisList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const columns = [
    { field: 'id', headerName: 'ID', width: 100 },
    { field: 'activeDevice', headerName: 'Active Device', width: 150 },
    { field: 'averageTimeUsage', headerName: 'Average Time Usage(hr)', width: 180 },
    {
      field: 'networkDevice.hardwareName',
      headerName: 'Network Device Name',
      width: 200,
      valueGetter: (params) => params.row.networkDevice.hardwareName,
    },
    {
      field: 'networkDevice.manufacturer.name',
      headerName: 'Manufacturer Name',
      width: 200,
      valueGetter: (params) => params.row.networkDevice.manufacturer.name,
    },
    {
      field: 'networkDevice.quantity',
      headerName: 'Quantity',
      width: 120,
      valueGetter: (params) => params.row.networkDevice.quantity,
    },
    {
      field: 'networkDevice.cost',
      headerName: 'Cost',
      width: 100,
      valueGetter: (params) => params.row.networkDevice.cost,

    },
    {
      field: 'companyRating',
      headerName: 'Company Rating',
      width: 150,

    }
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
          <DataGrid
            rows={networkDeviceAnalysisList}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 5 },
              },
            }}
            pageSizeOptions={[5, 10]}
          />
        </div>
  );
}

export default NetworkDeviceAnalysisTable;
