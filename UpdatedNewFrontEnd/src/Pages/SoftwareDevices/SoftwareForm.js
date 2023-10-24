import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


const SoftwareForm = () => {
  const [softwareData, setSoftwareData] = useState({
    manufacturer: {
      name: '', // Updated to store the selected manufacturer's name
      fieldOfWork: 'Software',
    },
    softwareName: '',
    purchaseDate: '',
    expiryDate: '',
    typeOfPlan: '',
    usersCanUse: '',
    priceOfSoftware: '',
    licenseKey: '',
  });

  const [error, setError] = useState('');
  const [manufacturers, setManufacturers] = useState([]);
  const [responseMessage, setResponseMessage] = useState('');

  useEffect(() => {
    fetch('http://localhost:8080/api/allManufacture')
      .then((response) => response.json())
      .then((data) => setManufacturers(data))
      .catch((error) => console.error('Error fetching manufacturers: ', error));
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === 'manufacturer.name') {
      setSoftwareData({
        ...softwareData,
        manufacturer: {
          name: value, // Update manufacturer.name
          fieldOfWork: softwareData.manufacturer.fieldOfWork,
        },
      });
    } else {
      setSoftwareData({
        ...softwareData,
        [name]: value,
      });
    }
  };

  const isSubmitDisabled = () => {
    // Check if any of the required fields are empty
    return (
      !softwareData.softwareName ||
      !softwareData.purchaseDate ||
      !softwareData.expiryDate ||
      !softwareData.typeOfPlan ||
      !softwareData.usersCanUse ||
      !softwareData.priceOfSoftware ||
      !softwareData.licenseKey
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isSubmitDisabled()) {
      setError('Please fill in all required fields before submitting.');
      return;
    }

    const softwareDTO = {
      software: {
        softwareName: softwareData.softwareName,
        purchaseDate: softwareData.purchaseDate,
        expiryDate: softwareData.expiryDate,
        typeOfPlan: softwareData.typeOfPlan,
        usersCanUse: softwareData.usersCanUse,
        priceOfSoftware: softwareData.priceOfSoftware,
        licenseKey: softwareData.licenseKey,
      },
      manufacturer: {
        name: softwareData.manufacturer.name,
        fieldOfWork: softwareData.manufacturer.fieldOfWork,
      },
    };

    fetch('http://localhost:8080/api/addSoftware', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(softwareDTO),
    })
      .then((response) => response.json())
      .then((data) => {
        setResponseMessage(data.responseBody);
        setSoftwareData({
          manufacturer: {
            name: '', // Reset manufacturer name
            fieldOfWork: 'Software',
          },
          softwareName: '',
          purchaseDate: '',
          expiryDate: '',
          typeOfPlan: '',
          usersCanUse: '',
          priceOfSoftware: '',
          licenseKey: '',
        });

        // Show a success toast
        toast.success('Software added successfully');
      })
      .catch((error) => {
        console.error('Error adding software: ', error);
        setError('An error occurred.');

        // Show an error toast
        toast.error('An error occurred while adding software');
      });
  };

  return (
    <div className="form-container">
      <h2 className="form-label">Add Software</h2>
      <form onSubmit={handleSubmit}>
        <label className="form-label">
          <Link to="/manufacturerForm">New Manufacturer?</Link>
          <br />
          Manufacturer:
          <select
            name="manufacturer.name"
            value={softwareData.manufacturer.name}
            onChange={handleChange}
            className="form-input"
          >
            <option value="">Select a manufacturer</option>
            {manufacturers
              .filter((manufacturer) => manufacturer.fieldOfWork === 'Software')
              .map((manufacturer) => (
                <option key={manufacturer.id} value={manufacturer.name}>
                  {manufacturer.name}
                </option>
              ))}
          </select>
        </label>

        <label className="form-label">
          Field of Work:
          <input
            type="text"
            name="manufacturer.fieldOfWork"
            value={softwareData.manufacturer.fieldOfWork}
            readOnly
            className="form-input"
          />
        </label>
        <label className="form-label">
          Software Name:
          <input
            type="text"
            name="softwareName"
            value={softwareData.softwareName}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          License Key:
          <input
            type="text"
            name="licenseKey"
            value={softwareData.licenseKey}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          Purchase Date:
          <input
            type="date"
            name="purchaseDate"
            value={softwareData.purchaseDate}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          Expiry Date:
          <input
            type="date"
            name="expiryDate"
            value={softwareData.expiryDate}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          Type of Plan:
          <input
            type="text"
            name="typeOfPlan"
            value={softwareData.typeOfPlan}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          Users Can Use:
          <input
            type="number"
            name="usersCanUse"
            value={softwareData.usersCanUse}
            onChange={handleChange}
          />
        </label>
        <label className="form-label">
          Price of Software:
          <input
            type="number"
            name="priceOfSoftware"
            value={softwareData.priceOfSoftware}
            onChange={handleChange}
          />
        </label>
        <button type="submit" disabled={isSubmitDisabled()} className="form-button">
          Add Software
        </button>
        {error && <div className="form-error">{error}</div>}
      </form>
      <div className="form-message">{responseMessage}</div>

      <ToastContainer

position="top-right"

autoClose={2000}

hideProgressBar={false}

newestOnTop={false}

closeOnClick

rtl={false}

pauseOnFocusLoss

draggable

pauseOnHover

theme="light"

style={{ position: "fixed", top: "60px", right: "0" }}

/>
    </div>
  );
};

export default SoftwareForm;
