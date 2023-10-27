import {Route, Routes } from "react-router-dom";
import Manufacturer from "../../Pages/Manufacturer";
import Dashboard from "../../Pages/Dashbaord";
import NetworkDevices from "../../Pages/NetworkDevices";
import SoftwareDevices from "../../Pages/SoftwareDevices";
import TotalSoftwareDevices from "../../Pages/SoftwareDevices/TotalSoftwareDevices";
import LessThan45Days from "../../Pages/SoftwareDevices/LessThan45Days";
import LessThanZeroDays from "../../Pages/SoftwareDevices/LessThanZeroDays";
import MoreThan45Days from "../../Pages/SoftwareDevices/MoreThan45days";
import SoftwareForm from "../../Pages/SoftwareDevices/SoftwareForm";
import ManufacturerForm from "../../Pages/Manufacturer/ManufacturerForm";
import RenewSoftwareForm from "../../Pages/SoftwareServices/RenewSoftwareForm";
import SoftwareChangePlanForm from "../../Pages/SoftwareServices/SoftwareChangePlanForm";
import Services from "../../Pages/SoftwareServices";
import SoftwareAnalysisForm from "../../Pages/SoftwareServices/SoftwareAnalysisForm";
import NetworkServices from "../../Pages/NetworkServices";
import NetworkDeviceRMAForm from "../../Pages/NetworkServices/NetworkDeviceRMAForm";
import NetworkDeviceAnalysisForm from "../../Pages/NetworkServices/NetworkDeviceAnalysisForm";
import AllHistory from "../../Pages/AllHistory";
import ManufacturerHistory from "../../Pages/AllHistory/ManufacturerHistory";
import SoftwareHistory from "../../Pages/AllHistory/SoftwareHistory";
import LoginPage from "../../Pages/Login/LoginPage";



function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />}></Route>
      <Route path="/SoftwareDevices" element={<SoftwareDevices />}></Route>
      <Route path="/NetworkDevices" element={<NetworkDevices />}></Route>
      <Route path="/Manufacturer" element={<Manufacturer />}></Route>
      <Route path='/TotalSoftwareDevices' element={<TotalSoftwareDevices />}></Route>
      <Route path='/LessThan45Days' element={<LessThan45Days />}></Route>
      <Route path='/MoreThan45Days' element={<MoreThan45Days />}></Route>
      <Route path='/LessThanZeroDays' element={<LessThanZeroDays />}></Route>
      <Route path='/softwareForm' element={<SoftwareForm />}></Route>
      <Route path='/manufacturerForm' element={<ManufacturerForm />}></Route>
      <Route path='/RenewSoftwareForm' element={<RenewSoftwareForm />}></Route>
      <Route path='/softwareChangePlanForm' element={<SoftwareChangePlanForm />}></Route>
      <Route path='/Services' element={<Services />}></Route>
      <Route path='/SoftwareAnalysisForm' element={<SoftwareAnalysisForm />}></Route>
      <Route path='/NetworkServices' element={<NetworkServices />}></Route>
      <Route path='/NetworkDeviceRMAForm' element={<NetworkDeviceRMAForm />}></Route>
      <Route path='/NetworkDeviceAnalysisForm' element={<NetworkDeviceAnalysisForm />}></Route>
      <Route path='/AllHistory' element={<AllHistory />}></Route>
      <Route path='/ManufacturerHistory' element={<ManufacturerHistory />}></Route>
      <Route path='/SoftwareHistory' element={<SoftwareHistory />}></Route>
      <Route path='/login' element={<LoginPage />}></Route>
    </Routes>
  );
}
export default AppRoutes;
