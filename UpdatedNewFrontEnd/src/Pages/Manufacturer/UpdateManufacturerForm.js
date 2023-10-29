import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'react-toastify/dist/ReactToastify.css';
import { toast } from 'react-toastify';


const UpdateManufacturerForm = ({ selectedManufacturerId }) => {
  const [manufacturerData, setManufacturerData] = useState({
    name: '',
    companyWebsiteLink: '',
    emailId: '',
    fieldOfWork: 'Software',
  });
  const [successMessage, setSuccessMessage] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Fetch the selected manufacturer's data using the selectedManufacturerId
    if (selectedManufacturerId) {
      setLoading(true);
      axios
        .get(`http://localhost:8080/api/getManufacturer/${selectedManufacturerId}`,{headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }})
        .then((response) => {
          const selectedManufacturerData = response.data;
          setManufacturerData(selectedManufacturerData);
          setLoading(false);
        })
        .catch((error) => {
          console.error('Error fetching selected manufacturer data: ', error);
          setLoading(false);
        });
    }
  }, [selectedManufacturerId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setManufacturerData({
      ...manufacturerData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios
    .put(`http://localhost:8080/api/updateManufacturer/${selectedManufacturerId}`, manufacturerData, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
    .then((response) => {
      // Handle the response (e.g., show a success message)
      console.log('Manufacturer updated:', response.data);
      setSuccessMessage('Manufacturer updated successfully');
      // Clear the success message after a few seconds
      setTimeout(() => {
        setSuccessMessage('');
      }, 5000);
      toast.success('Manufacturer updated successfully!');
    })
    .catch((error) => {
      // Handle errors (e.g., show an error message)
      console.error('Error updating manufacturer:', error);
      toast.error('Error updating manufacturer!');

    });
  };  
  const isFormComplete = () => {
    return (
      !manufacturerData.name ||
      !manufacturerData.companyWebsiteLink ||
      !manufacturerData.emailId
    );
  };
  
  return (
    <div className="form-container" style={{'top': '80%'}}>
      <h2 className="form-label">Update Manufacturer</h2>
      {successMessage && <p className="success-message">{successMessage}</p>}
      <form onSubmit={handleSubmit}>
        <label className="form-label">
          Name:
          <input
            type="text"
            name="name"
            value={manufacturerData.name}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Company Website Link:
          <input
            type="text"
            name="companyWebsiteLink"
            value={manufacturerData.companyWebsiteLink}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Email ID:
          <input
            type="text"
            name="emailId"
            value={manufacturerData.emailId}
            onChange={handleInputChange}
            className="form-input"
          />
        </label>
        <label className="form-label">
          Field of Work:
          <select
            name="fieldOfWork"
            value={manufacturerData.fieldOfWork}
            onChange={handleInputChange}
            className="form-input"
          >
            <option value="Software">Software</option>
            <option value="Hardware">Hardware</option>
          </select>
        </label>
        <button type="submit" disabled={isFormComplete()} className="form-button">
          Update Manufacturer
        </button>
      </form>
    </div>
  );
}

export default UpdateManufacturerForm;
