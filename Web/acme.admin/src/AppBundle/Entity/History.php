<?php

namespace AppBundle\Entity;

/**
 * History
 */
class History
{
    /**
     * @var \DateTime
     */
    private $date;

    /**
     * @var integer
     */
    private $statut;

    /**
     * @var integer
     */
    private $actionType;

    /**
     * @var boolean
     */
    private $isuserdestination;

    /**
     * @var integer
     */
    private $id;

    /**
     * @var \AppBundle\Entity\Utilisateur
     */
    private $utilisateur;

    /**
     * @var \AppBundle\Entity\Album
     */
    private $album;


    /**
     * Set date
     *
     * @param \DateTime $date
     *
     * @return History
     */
    public function setDate($date)
    {
        $this->date = $date;

        return $this;
    }

    /**
     * Get date
     *
     * @return \DateTime
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * Set statut
     *
     * @param integer $statut
     *
     * @return History
     */
    public function setStatut($statut)
    {
        $this->statut = $statut;

        return $this;
    }

    /**
     * Get statut
     *
     * @return integer
     */
    public function getStatut()
    {
        return $this->statut;
    }

    /**
     * Set actionType
     *
     * @param integer $actionType
     *
     * @return History
     */
    public function setActionType($actionType)
    {
        $this->actionType = $actionType;

        return $this;
    }

    /**
     * Get actionType
     *
     * @return integer
     */
    public function getActionType()
    {
        return $this->actionType;
    }

    /**
     * Set isuserdestination
     *
     * @param boolean $isuserdestination
     *
     * @return History
     */
    public function setIsuserdestination($isuserdestination)
    {
        $this->isuserdestination = $isuserdestination;

        return $this;
    }

    /**
     * Get isuserdestination
     *
     * @return boolean
     */
    public function getIsuserdestination()
    {
        return $this->isuserdestination;
    }

    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set utilisateur
     *
     * @param \AppBundle\Entity\Utilisateur $utilisateur
     *
     * @return History
     */
    public function setUtilisateur(\AppBundle\Entity\Utilisateur $utilisateur = null)
    {
        $this->utilisateur = $utilisateur;

        return $this;
    }

    /**
     * Get utilisateur
     *
     * @return \AppBundle\Entity\Utilisateur
     */
    public function getUtilisateur()
    {
        return $this->utilisateur;
    }

    /**
     * Set album
     *
     * @param \AppBundle\Entity\Album $album
     *
     * @return History
     */
    public function setAlbum(\AppBundle\Entity\Album $album = null)
    {
        $this->album = $album;

        return $this;
    }

    /**
     * Get album
     *
     * @return \AppBundle\Entity\Album
     */
    public function getAlbum()
    {
        return $this->album;
    }
}
