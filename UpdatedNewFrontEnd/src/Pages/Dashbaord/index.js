import {
  BsFillArchiveFill,
  BsFillGrid3X3GapFill,
  BsPeopleFill,
  BsFillBellFill
} from "react-icons/bs";
import { Card, Space, Table, Typography } from "antd";
import { useEffect, useState } from "react";
import { getLicenseCounts, getOrders, getRevenue } from "../../API";
import { useNavigate } from 'react-router-dom';
import './Dashboard.css'


import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import SideMenu from "../../Components/SideMenu";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function Dashboard() {


  const navigate = useNavigate();
  const handleNavigateToSoftwareDevices = () => {
    navigate('/TotalSoftwareDevices'); // Change the route as needed
  };

  const handleNavigateLessThan45Days = () => {
    navigate('/LessThan45Days'); // Change the route as needed    
  };

  const handleNavigateLessThanZeroDays = () => {
    navigate('/LessThanZeroDays'); // Change the route as needed
  };

  const handleNavigateMoreThan45Days = () => {
    navigate('/MoreThan45Days'); // Change the route as needed
  };

  const [licenseCounts, setLicenseCounts] = useState(
    {
      totalLicenses: 0,
      licensesGreaterThan45: 0,
      licensesLessThan45Count: 0,
      licensesLessThanZero: 0
    }
  );


  useEffect(() => {
    getLicenseCounts().then((res) => {
      setLicenseCounts(res);
    });
  }, []);


  return (
    <div className="SideMenuAndPageContent">
      <SideMenu />
      <div className="PageContent">
        <Space size={20} direction="vertical">
          <Typography.Title level={4}>Dashboard</Typography.Title>
          <div className='main-cards'>
            <div className='card' onClick={handleNavigateToSoftwareDevices} style={{ cursor: 'pointer' }}>
              <div className='card-inner'>
                <h3>TOTAL LICENSE</h3>
                <BsFillArchiveFill className='card_icon' />
              </div>
              <h1>{licenseCounts.totalLicenses}</h1>
            </div>
            <div className='card' onClick={handleNavigateLessThan45Days} style={{ cursor: 'pointer' }}>
              <div className='card-inner'>
                <h3>ABOUT TO EXPIRE</h3>
                <BsFillGrid3X3GapFill className='card_icon' />
              </div>
              <h1>{licenseCounts.licensesLessThan45Count}</h1>
            </div>
            <div className='card' onClick={handleNavigateMoreThan45Days} style={{ cursor: 'pointer' }}>
              <div className='card-inner'>
                <h3>ACTIVE LICENSE</h3>
                <BsPeopleFill className='card_icon' />
              </div>
              <h1>{licenseCounts.licensesGreaterThan45}</h1>
            </div>
            <div className='card' onClick={handleNavigateLessThanZeroDays} style={{ cursor: 'pointer' }}>
              <div className='card-inner'>
                <h3>EXPIRED LICENSE</h3>
                <BsFillBellFill className='card_icon' />
              </div>
              <h1>{licenseCounts.licensesLessThanZero}</h1>
            </div>
          </div>
          <Space>
            <RecentOrders />
            <DashboardChart />
          </Space>
        </Space>
      </div>
    </div>
  );
}


function RecentOrders() {
  const [dataSource, setDataSource] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getOrders().then((res) => {
      setDataSource(res.products.splice(0, 3));
      setLoading(false);
    });
  }, []);

  return (
    <>
      <Typography.Text>Recent Orders</Typography.Text>
      <Table
        columns={[
          {
            title: "Title",
            dataIndex: "title",
          },
          {
            title: "Quantity",
            dataIndex: "quantity",
          },
          {
            title: "Price",
            dataIndex: "discountedPrice",
          },
        ]}
        loading={loading}
        dataSource={dataSource}
        pagination={false}
      ></Table>
    </>
  );
}
function DashboardChart() {
  const [actionData, setActionData] = useState({
    labels: [],
    datasets: [],
  });

  useEffect(() => {
    // Fetch data from your API endpoint for action counts
    fetch("YOUR_ACTION_COUNT_API_ENDPOINT")
      .then((response) => response.json())
      .then((data) => {
        const labels = data.map((item) => item.actionType);
        const counts = data.map((item) => item.count);

        const actionChartData = {
          labels,
          datasets: [
            {
              label: "Action Counts",
              data: counts,
              backgroundColor: "rgba(0, 128, 255, 0.6)",
            },
          ],
        };

        setActionData(actionChartData);
      })
      .catch((error) => {
        console.error("Error fetching action data:", error);
      });
  }, []);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: false, // Hide legend for this chart
      },
      title: {
        display: true,
        text: "Action Counts",
      },
    },
  };

  return (
    <Card style={{ width: 500, height: 250 }}>
      <Bar options={options} data={actionData} />
    </Card>
  );
}

export default Dashboard;