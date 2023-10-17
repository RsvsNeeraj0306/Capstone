import {
  DollarCircleOutlined,
  ShoppingCartOutlined,
  ShoppingOutlined,
  UserOutlined,
} from "@ant-design/icons";
import {
  BsFillArchiveFill,
  BsFillGrid3X3GapFill,
  BsPeopleFill,
  BsFillBellFill,
  BsFillEnvelopeFill,
  BsPersonCircle,
  BsSearch,
  BsJustify,
} from "react-icons/bs";
import { Card, Space, Statistic, Table, Typography } from "antd";
import { useEffect, useState } from "react";
import { getLicenseCounts, getOrders, getRevenue } from "../../API";
import { useNavigate } from 'react-router-dom';


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

  const handleNavigateLessThanZeroDays =( ) => {  
    navigate('/LessThanZeroDays'); // Change the route as needed
  };

  const handleNavigateMoreThan45Days = () => {
    navigate('/MoreThan45Days'); // Change the route as needed
  };

    const [licenseCounts, setLicenseCounts] = useState(
      {totalLicenses: 0, 
      licensesGreaterThan45: 0,
      licensesLessThan45Count: 0,
      licensesLessThanZero: 0}
    );


  useEffect(() => {
    getLicenseCounts().then((res) => {
      setLicenseCounts(res);
    } );
  }, []);


  return (
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
        <div className='card' onClick={handleNavigateLessThan45Days} style={{cursor:'pointer'}}>
          <div className='card-inner'>
            <h3>ABOUT TO EXPIRE</h3>
            <BsFillGrid3X3GapFill className='card_icon' />
          </div>
          <h1>{licenseCounts.licensesLessThan45Count}</h1>
        </div>
        <div className='card' onClick={handleNavigateMoreThan45Days} style={{cursor:'pointer'}}>
          <div className='card-inner'>
            <h3>ACTIVE LICENSE</h3>
            <BsPeopleFill className='card_icon' />
          </div>
          <h1>{licenseCounts.licensesGreaterThan45}</h1>
        </div>
        <div className='card' onClick={handleNavigateLessThanZeroDays} style={{cursor:'pointer'}}>
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
  );
}

function DashboardCard({ title, value, icon }) {
  return (
    <Card>
      <Space direction="horizontal">
        {icon}
        <Statistic title={title} value={value} />
      </Space>
    </Card>
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
  const [reveneuData, setReveneuData] = useState({
    labels: [],
    datasets: [],
  });

  useEffect(() => {
    getRevenue().then((res) => {
      const labels = res.carts.map((cart) => {
        return `User-${cart.userId}`;
      });
      const data = res.carts.map((cart) => {
        return cart.discountedTotal;
      });

      const dataSource = {
        labels,
        datasets: [
          {
            label: "Revenue",
            data: data,
            backgroundColor: "rgba(255, 0, 0, 1)",
          },
        ],
      };

      setReveneuData(dataSource);
    });
  }, []);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: "bottom",
      },
      title: {
        display: true,
        text: "Order Revenue",
      },
    },
  };

  return (
    <Card style={{ width: 500, height: 250 }}>
      <Bar options={options} data={reveneuData} />
    </Card>
  );
}
export default Dashboard;
