import { BrowserRouter, Route, Routes } from "react-router-dom";
import Customers from "../../Pages/Customers";
import Dashboard from "../../Pages/Dashbaord";
import NetworkDevices from "../../Pages/NetworkDevices";
import SoftwareDevices from "../../Pages/SoftwareDevices";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />}></Route>
      <Route path="/SoftwareDevices" element={<SoftwareDevices />}></Route>
      <Route path="/NetworkDevices" element={<NetworkDevices />}></Route>
      <Route path="/customers" element={<Customers />}></Route>
    </Routes>
  );
}
export default AppRoutes;
