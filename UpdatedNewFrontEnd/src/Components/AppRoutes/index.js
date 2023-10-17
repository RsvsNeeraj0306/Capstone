import { BrowserRouter, Route, Routes } from "react-router-dom";
import Customers from "../../Pages/Customers";
import Dashboard from "../../Pages/Dashbaord";
import NetworkDevices from "../../Pages/NetworkDevices";
import SoftwareDevices from "../../Pages/SoftwareDevices";
import TotalSoftwareDevices from "../../Pages/SoftwareDevices/TotalSoftwareDevices";
import LessThan45Days from "../../Pages/SoftwareDevices/LessThan45Days";
import LessThanZeroDays from "../../Pages/SoftwareDevices/LessThanZeroDays";
import MoreThan45Days from "../../Pages/SoftwareDevices/MoreThan45days";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />}></Route>
      <Route path="/SoftwareDevices" element={<SoftwareDevices />}></Route>
      <Route path="/NetworkDevices" element={<NetworkDevices />}></Route>
      <Route path="/customers" element={<Customers />}></Route>
      <Route path='/TotalSoftwareDevices' element={<TotalSoftwareDevices />}></Route>
      <Route path='/LessThan45Days' element={<LessThan45Days />}></Route>
      <Route path='/MoreThan45Days' element={<MoreThan45Days />}></Route>
      <Route path='/LessThanZeroDays' element={<LessThanZeroDays />}></Route>
    </Routes>
  );
}
export default AppRoutes;
