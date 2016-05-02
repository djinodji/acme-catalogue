<?
class Abonnement{
function verifyFields($params)
{
	$error = '';

	return $error;
}

function add($params)
{
	global $db;

	if (($errors = $this->verifyFields($params)) != '')
	{
		return $errors;
	}

	$data = array(
		'Utilisateur_id' => $params['Utilisateur_id'],
		'Album_id' => $params['Album_id'],
		'status' => $params['status']
	);

	//Returns either the new ID of the row inserted or FALSE on failure 
	return $db->query_insert('Abonnement', $data);
}

function edit($id, $params)
{
	global $db;

	if (($errors = $this->verifyFields($params)) != '')
	{
		return $errors;
	}

	$data = array(
		'Utilisateur_id' => $params['Utilisateur_id'],
		'Album_id' => $params['Album_id'],
		'status' => $params['status']
	);

	return $db->query_update('Abonnement', $data, '  = ' . $id);
}

function delete($id)
{
	global $db;

	return $db->query(sprintf('DELETE FROM `Abonnement` WHERE  = %d', $id));
}

function getAll($startIndex = null, $numRows = null, $orderBy = null, $orderDirection = 'asc')
{
	global $db;

	if (($startIndex != null) && (!is_numeric($startIndex) || ($startIndex < 0)))
	{
		return false;
	}

	if (($numRows != null) && (!is_numeric($numRows) || ($numRows < 0)))
	{
		return false;
	}

	$order = '';
	$limit = '';
	$fieldsArray = array('Utilisateur_id', 'Album_id', 'status');

	if (($orderBy != null) && !in_array($orderBy, $fieldsArray))
	{
		return false;
	}

	if ((strtolower($orderDirection) != 'asc') && (strtolower($orderDirection) != 'desc'))
	{
		return false;
	}

	if (($startIndex != null) && ($numRows != null))
	{
		$limit = sprintf(' LIMIT %d, %d', $startIndex, $numRows);
	}
	else if (($startIndex != null) && ($numRows == null))
	{
		$limit = sprintf(' LIMIT %d', $startIndex);
	}

	if (($orderBy != null) && ($orderDirection != null))
	{
		$order = sprintf(' ORDER BY %s %s', $orderBy, $orderDirection);
	}
	else if (($orderBy != null) && ($orderDirection == null))
	{
		$order = sprintf(' ORDER BY %s ASC ', $orderBy);
	}

	$sql = sprintf("SELECT `Utilisateur_id`, `Album_id`, `status` FROM `Abonnement` %s %s", $order, $limit);
	return $db->fetch_all_array($sql);
}
}
?>
