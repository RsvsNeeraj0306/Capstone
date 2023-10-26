import {
  AppstoreOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Menu } from "antd";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import TerminalIcon from '@mui/icons-material/Terminal';
import DeviceHubIcon from '@mui/icons-material/DeviceHub';
import DesignServicesIcon from '@mui/icons-material/DesignServices';
import StorefrontIcon from '@mui/icons-material/Storefront';
import HistoryIcon from '@mui/icons-material/History';


function SideMenu() {
  const location = useLocation();
  const [selectedKeys, setSelectedKeys] = useState("/");

  useEffect(() => {
    const pathName = location.pathname;
    setSelectedKeys(pathName);
  }, [location.pathname]);

  const navigate = useNavigate();
  return (
    <div className="SideMenu">
      <Menu
        className="SideMenuVertical"
        mode="vertical"
        onClick={(item) => {
          //item.key
          navigate(item.key);
        }}
        selectedKeys={[selectedKeys]}
        items={[
          {
            label: "Dashbaord",
            icon: <AppstoreOutlined />,
            key: "/",
          },
          {
            label: "Software Devices",
            key: "/SoftwareDevices",
            icon: <TerminalIcon />,
          },
          {
            label: "Network Devices",
            key: "/NetworkDevices",
            icon: <DeviceHubIcon />,
          },
          {
            label: "Compamy Manufaturers",
            key: "/Manufacturer",
            icon: <UserOutlined />,
          },
          {
            label: "Software Services",
            key: "/Services",
            icon: <DesignServicesIcon />,

          },
          {
            label:"Network Services",
            key:"/NetworkServices",
            icon:<StorefrontIcon />

          },

          {
            label:"History",
            key:"/AllHistory",
            icon:<HistoryIcon />
          }
        ]}
      ></Menu>
    </div>
  );
}
export default SideMenu;
